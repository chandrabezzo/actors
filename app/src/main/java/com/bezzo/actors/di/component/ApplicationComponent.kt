package com.bezzo.actors.di.component

import android.app.Application
import android.content.Context
import com.bezzo.actors.MvpApp
import com.bezzo.actors.data.local.LocalStorageHelper
import com.bezzo.actors.data.network.ApiHelper
import com.bezzo.actors.data.session.SessionHelper
import com.bezzo.actors.di.ApplicationContext
import com.bezzo.actors.di.module.ApplicationModule
import com.bezzo.actors.service.MessagingInstanceIDService
import com.bezzo.actors.service.MessagingService
import com.bezzo.actors.service.UpdateLocationService
import dagger.Component
import javax.inject.Singleton

/**
 * Created by bezzo on 26/09/17.
 */

@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {

    fun inject(app: MvpApp)

    fun inject(messagingInstanceIDService: MessagingInstanceIDService)

    fun inject(messagingService: MessagingService)

    fun inject(updateLocationService: UpdateLocationService)

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun apiHelper() : ApiHelper

    fun sessionHelper() : SessionHelper

    fun localHelper() : LocalStorageHelper
}
