package com.bezzo.actors.data.network

import com.bezzo.actors.base.BaseResponse
import io.reactivex.functions.Consumer

/**
 * Created by bezzo on 19/10/17.
 */

abstract class ResponseHandler<M : BaseResponse> constructor(val successCode : Int) : Consumer<M> {

    abstract fun onSuccess(model : M)

    abstract fun onUnauthorized()

    abstract fun onError(model : M)

    override fun accept(model : M) {
        when {
            model.code == successCode -> onSuccess(model)
            model.code == 401 -> onUnauthorized()
            else -> onError(model)
        }
    }
}