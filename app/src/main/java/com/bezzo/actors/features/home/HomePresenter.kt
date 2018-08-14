package com.bezzo.actors.features.home

import com.androidnetworking.error.ANError
import com.bezzo.actors.base.BasePresenter
import com.bezzo.actors.data.local.LocalStorageHelper
import com.bezzo.actors.data.model.*
import com.bezzo.actors.data.network.ApiHelper
import com.bezzo.actors.data.network.ResponseHandler
import com.bezzo.actors.data.network.ResponseOkHttp
import com.bezzo.actors.data.session.SessionHelper
import com.bezzo.actors.util.AppLogger
import com.bezzo.actors.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import okhttp3.Response
import java.util.concurrent.Executors
import javax.inject.Inject


/**
 * Created by bezzo on 24/01/18.
 * if you use kotlin, when send to view you must add "?" for null check pointer
 * but if you use java, when send to view you must add if(!isViewAttached) return;
 * before you send data to view
 */

class HomePresenter<V : HomeContracts.View> @Inject
constructor(apiHelper: ApiHelper, sessionHelper: SessionHelper, localHelper: LocalStorageHelper,
            schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable)
    : BasePresenter<V>(apiHelper, sessionHelper, localHelper, schedulerProvider, compositeDisposable), HomeContracts.Presenter<V> {

    override fun getActorsApi() {
        apiHelper.getActors()
                .getAsOkHttpResponseAndObject(ActorsResponse::class.java, object : ResponseOkHttp<ActorsResponse>(200){
                    override fun onSuccess(response: Response, model: ActorsResponse) {
                        view?.showAll(model.actors!!)
                    }

                    override fun onUnauthorized() {

                    }

                    override fun onFailed(response: Response) {
                        logging(response.message())
                    }

                    override fun onHasError(error: ANError) {
                        handleApiError(error)
                    }

                })
    }

    override fun getActors() {
        compositeDisposable.add(localHelper.dycodeDatabase.actor()
                .getAll().compose(schedulerProvider.ioToMainFlowableScheduler())
                .subscribe({
                    view?.hideRefreshing()
                    view?.showAll(it)
                }, {
                    logging(it.toString())
                }))
    }
}