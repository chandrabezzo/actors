package com.bezzo.actors.data.network

import com.androidnetworking.common.Priority
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.bezzo.actors.BuildConfig
import com.bezzo.actors.MvpApp
import com.bezzo.actors.util.AppLogger
import com.rx2androidnetworking.Rx2ANRequest
import com.rx2androidnetworking.Rx2AndroidNetworking
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object RestApi {

    var okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(HttpInterceptor())
            .build()

    var httpClientUpload = OkHttpClient().newBuilder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(HttpInterceptor())
            .build()

    fun get(endpoint: String, params: Map<String, String>?,
            paths: Map<String, String>?, headers: Map<String, String>?): Rx2ANRequest {

        val getRequest = Rx2AndroidNetworking.get(endpoint)

        if (headers != null) {
            getRequest.addHeaders(headers)
        }

        if (params != null) {
            getRequest.addQueryParameter(params)
        }

        if (paths != null) {
            getRequest.addPathParameter(paths)
        }

        getRequest.setPriority(Priority.LOW)

        getRequest.setOkHttpClient(okHttpClient)

        return getRequest.build()
    }

    fun post(endpoint: String, params : Map<String, String>?, paths: Map<String, String>?,
             headers: Map<String, String>?, body: Any?): Rx2ANRequest {

        val postRequest = Rx2AndroidNetworking.post(endpoint)
        AppLogger.i("endpoint : $endpoint")

        if (params != null){
            postRequest.addQueryParameter(params)
        }

        if (paths != null) {
            postRequest.addPathParameter(paths)
        }

        if (headers != null) {
            postRequest.addHeaders(headers)
        }

        postRequest.addApplicationJsonBody(body)
        postRequest.setPriority(Priority.MEDIUM)

        postRequest.setOkHttpClient(okHttpClient)

        return postRequest.build()
    }

    fun put(endpoint: String, params : Map<String, String>?, paths: Map<String, String>?,
            headers: Map<String, String>?, body: Any?): Rx2ANRequest {

        val putRequest = Rx2AndroidNetworking.put(endpoint)

        if (params != null){
            putRequest.addQueryParameter(params)
        }

        if (headers != null) {
            putRequest.addHeaders(headers)
        }

        if (paths != null) {
            putRequest.addPathParameter(paths)
        }

        putRequest.addApplicationJsonBody(body)
        putRequest.setPriority(Priority.MEDIUM)

        putRequest.setOkHttpClient(okHttpClient)

        return putRequest.build()
    }

    fun delete(endpoint: String, params: Map<String, String>?, paths: Map<String, String>?,
               headers: Map<String, String>?, body: Any?): Rx2ANRequest {

        val deleteRequest = Rx2AndroidNetworking.delete(endpoint)

        if (params != null){
            deleteRequest.addQueryParameter(params)
        }

        if (headers != null) {
            deleteRequest.addHeaders(headers)
        }

        if (paths != null) {
            deleteRequest.addPathParameter(paths)
        }

        deleteRequest.addApplicationJsonBody(body)
        deleteRequest.setPriority(Priority.MEDIUM)
        deleteRequest.setOkHttpClient(okHttpClient)

        return deleteRequest.build()
    }

    fun download(endpoint: String, savedLocation: String, fileName: String,
                 params: Map<String, String>?, paths: Map<String, String>?,
                 headers: Map<String, String>?): Rx2ANRequest {

        val downloadBuilder = Rx2AndroidNetworking.download(endpoint,
                savedLocation, fileName)

        if (headers != null) {
            downloadBuilder.addHeaders(headers)
        }

        if (params != null) {
            downloadBuilder.addQueryParameter(params)
        }

        if (paths != null) {
            downloadBuilder.addPathParameter(paths)
        }

        downloadBuilder.setPercentageThresholdForCancelling(50)
        downloadBuilder.setExecutor(Executors.newSingleThreadExecutor())
        downloadBuilder.setPriority(Priority.MEDIUM)

        return downloadBuilder.build()
    }

    fun upload(endpoint: String, params: Map<String, String>?, paths: Map<String, String>?,
               headers: Map<String, String>?, parameterFile: String, file: File,
               multipart: Map<String, String>?): Rx2ANRequest {

        return Rx2AndroidNetworking.upload(endpoint)
                .addHeaders(headers)
                .addMultipartFile(parameterFile, file)
                .addMultipartParameter(multipart)
                .setExecutor(Executors.newSingleThreadExecutor())
                .setPriority(Priority.HIGH)
                .setOkHttpClient(httpClientUpload)
                .build()
    }

    fun uploads(endpoint: String, params: Map<String, String>, paths: Map<String, String>,
                headers: Map<String, String>, files: Map<String, File>, multipart: Map<String, String>): Rx2ANRequest {
        return Rx2AndroidNetworking.upload(endpoint)
                .addHeaders(headers)
                .addMultipartFile(files)
                .addMultipartParameter(multipart)
                .setExecutor(Executors.newSingleThreadExecutor())
                .setPriority(Priority.HIGH)
                .setOkHttpClient(httpClientUpload)
                .build()
    }
}