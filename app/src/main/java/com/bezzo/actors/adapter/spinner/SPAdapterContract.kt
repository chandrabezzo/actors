package com.bezzo.actors.adapter.spinner

/**
 * Created by bezzo on 11/01/18.
 */
interface SPAdapterContract<in V : Any> {

    fun update(values : V)

    fun clear()
}