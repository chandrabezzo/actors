package com.bezzo.actors.di.component

import com.bezzo.actors.di.PerService
import com.bezzo.actors.di.module.ServiceModule
import com.bezzo.actors.service.MessagingInstanceIDService
import com.bezzo.actors.service.MessagingService
import com.bezzo.actors.service.UpdateLocationService
import dagger.Component

/**
 * Created by bezzo on 26/09/17.
 */

@PerService
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = [(ServiceModule::class)])
interface ServiceComponent {

    fun inject(messagingInstanceIDService: MessagingInstanceIDService)

    fun inject(messagingService: MessagingService)

    fun inject(updateLocationService: UpdateLocationService)
}