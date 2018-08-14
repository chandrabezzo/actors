package com.bezzo.actors.di.module

import android.app.Application
import android.content.Context
import com.bezzo.actors.data.local.LocalStorageHelper
import com.bezzo.actors.data.network.ApiHelper
import com.bezzo.actors.data.network.ApiHelperContract
import com.bezzo.actors.data.session.SessionHelper
import com.bezzo.actors.di.ApplicationContext
import com.bezzo.actors.util.SchedulerProvider
import com.bezzo.actors.util.constanta.AppConstans
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by bezzo on 26/09/17.
 */

@Module
class ApplicationModule(private val mApplication: Application) {

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return mApplication
    }

    @Provides
    fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    fun provideDatabaseName(): String {
        return AppConstans.DB_NAME
    }

    @Provides
    @Singleton
    fun provideLocalStorageHelper() = LocalStorageHelper(provideContext())

    @Provides
    @Singleton
    fun provideSessionHelper() = SessionHelper()

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelper): ApiHelperContract {
        return apiHelper
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider()
}
