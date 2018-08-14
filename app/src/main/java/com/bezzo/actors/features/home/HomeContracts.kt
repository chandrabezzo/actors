package com.bezzo.actors.features.home

import com.bezzo.actors.base.BaseActivityView
import com.bezzo.actors.base.BasePresenterContract
import com.bezzo.actors.data.model.*

class HomeContracts {

    interface View : BaseActivityView {
        fun showAll(values : List<Actors>)

        fun hideRefreshing()
    }

    interface Presenter<V : View> : BasePresenterContract<V> {
        fun getActorsApi()

        fun getActors()
    }
}