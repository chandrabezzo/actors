package com.bezzo.actors.data.network


import com.bezzo.actors.data.session.SessionConstants
import com.bezzo.actors.data.session.SessionHelper
import com.bezzo.actors.util.AppLogger
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit


class HttpInterceptor : Interceptor {

    var sessionHelper = SessionHelper()

    var urlLog : String? = null
    var headersLog : String? = null
    var bodyLog : String? = null
    var methodLog : String? = null
    var statusCodeLog : Int? = null

    var charset : Charset? = Charsets.UTF_8

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val token = sessionHelper.getSession(SessionConstants.TOKEN, "token")
        val requestBuilder = original.newBuilder()
                .addHeader(SessionConstants.TOKEN, token)
        val request = requestBuilder.build()

        val t1 = System.nanoTime()
        getRequestValue(request)

        val response = chain.proceed(request)

        // Get Execution Time
        val t2 = System.nanoTime()
        val executionTime = TimeUnit.MINUTES.convert((t2 - t1), TimeUnit.MILLISECONDS)

        // Get Response
        val buffer = getResponseValue(response)

        if (response.body()?.contentLength() != 0L) {
            AppLogger.i("Request ($methodLog) : $urlLog \n[Body] => \n$bodyLog \n[Headers] => \n$headersLog" +
                    "Excecution Time : $executionTime" +
                    "\nResponse ($statusCodeLog) : " + buffer?.clone()?.readString(charset))
        }

        return response
    }

    fun getRequestValue(value : Request){
        urlLog = value.url().toString()
        headersLog = value.headers().toString()
        bodyLog = value.body().toString()
        methodLog = value.method()
    }

    fun getResponseValue(value : Response) : Buffer? {
        val source = value.body()?.source()
        source?.request(Long.MAX_VALUE) // Buffer the entire body.
        var buffer = source?.buffer()

        val contentType = value.body()?.contentType()

        if (contentType != null) {
            charset = contentType.charset(Charsets.UTF_8)
        }

        statusCodeLog = value.code()

        return buffer
    }
}