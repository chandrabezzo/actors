package com.bezzo.actors.data.network

import com.bezzo.actors.data.model.ActorsResponse
import com.rx2androidnetworking.Rx2ANRequest
import io.reactivex.Observable

/**
 * Created by bezzo on 11/01/18.
 */
interface ApiHelperContract {

    fun getActors() : Rx2ANRequest
}