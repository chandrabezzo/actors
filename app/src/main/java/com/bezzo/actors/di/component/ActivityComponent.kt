package com.bezzo.actors.di.component

import com.bezzo.actors.di.PerActivity
import com.bezzo.actors.di.module.ActivityModule
import com.bezzo.actors.features.detail.DetailActivity
import com.bezzo.actors.features.home.HomeActivity
import dagger.Component

/**
 * Created by bezzo on 26/09/17.
 */

@PerActivity
@Component(
        dependencies = [(ApplicationComponent::class)],
        modules = [(ActivityModule::class)])
interface ActivityComponent {

    fun inject(homeActivity: HomeActivity)
    fun inject(detailActivity: DetailActivity)
}
