package com.bezzo.actors.data.network

import com.bezzo.actors.data.session.SessionHelper
import com.bezzo.actors.util.SchedulerProvider
import com.rx2androidnetworking.Rx2ANRequest
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by bezzo on 11/01/18.
 */

@Singleton
class ApiHelper @Inject
constructor(val schedulerProvider: SchedulerProvider) : ApiHelperContract {

    @Inject
    lateinit var session : SessionHelper

    override fun getActors(): Rx2ANRequest {
        return RestApi.get(ApiEndPoint.ACTOR, null, null, null)
    }
}
