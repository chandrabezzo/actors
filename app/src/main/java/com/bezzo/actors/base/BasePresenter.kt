package com.bezzo.actors.base

import android.widget.Toast
import com.androidnetworking.error.ANError
import com.bezzo.actors.data.local.LocalStorageHelper
import com.bezzo.actors.data.model.ApiError
import com.bezzo.actors.data.network.ApiHelper
import com.bezzo.actors.data.session.SessionConstants
import com.bezzo.actors.data.session.SessionHelper
import com.bezzo.actors.util.AppLogger
import com.bezzo.actors.util.CommonUtils
import com.bezzo.actors.util.SchedulerProvider
import com.google.gson.Gson
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.Executors
import javax.inject.Inject

/**
 * Created by bezzo on 26/09/17.
 */

open class BasePresenter<V : BaseView> @Inject
constructor(val apiHelper: ApiHelper,
            val sessionHelper : SessionHelper,
            val localHelper : LocalStorageHelper,
            val schedulerProvider: SchedulerProvider,
            val compositeDisposable: CompositeDisposable) : BasePresenterContract<V> {

    @Inject
    lateinit var gson: Gson

    var view: V? = null
        private set

    val isViewAttached: Boolean
        get() = view != null

    override fun onAttach(mvpView: V) {
        view = mvpView
    }

    override fun onDetach() {
        compositeDisposable.dispose()
        view = null
    }

    override fun handleApiError(error: ANError) {
        if (CommonUtils.isJSONValid(error.errorBody)){
            val apiError = gson.fromJson(error.errorBody,
                    ApiError::class.java)

            if (apiError != null) {
                if (error.errorCode == 401) {
                    logout()
                } else {
                    view?.showToast(apiError.message!!, Toast.LENGTH_SHORT)
                }
            } else {
                view?.showToast(error.message!!, Toast.LENGTH_SHORT)
            }
        }
        else {
            if (error.toString().contains("UnknownHost")){
                view?.handleError(1)
            }
            else if (error.toString().contains("timed out") || error.toString().contains("timeout")){
                view?.handleError(2)
            }
            else if (error.toString().contains("java") || error.toString().contains("html")){
                view?.handleError(3)
            }
            else if (error.errorBody != null) {
                if (error.errorBody.contains("html") || error.errorBody.contains("java")) {
                    view?.handleError(3)
                }
                else {
                    view?.handleError(6)
                }
            }
            else {
                view?.handleError(4)
            }
        }
    }

    override fun setUserAsLoggedOut() {

    }

    override fun clearLog() {
        sessionHelper.setLogin(false)
        sessionHelper.deleteSession(SessionConstants.TOKEN)

        val exec = Executors.newSingleThreadExecutor()
        exec.execute { }
    }

    override fun logout() {
        clearLog()

        view?.handleError(5)
        view?.openActivityOnTokenExpire()
    }

    companion object {
        private val TAG = "BasePresenter"
    }

    override fun logging(message: String?) {
        if (message != null){
            AppLogger.i(message)
            view?.showToast(message, Toast.LENGTH_SHORT)
        }
    }
}
