package com.bezzo.actors.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.bezzo.actors.di.PerActivity
import com.bezzo.actors.features.home.HomeContracts
import com.bezzo.actors.features.home.HomePresenter
import com.bezzo.actors.util.SchedulerProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by bezzo on 26/09/17.
 * Use @PerActivity if view of presenter is Activity
 */

@Module
class ActivityModule(private val mActivity: AppCompatActivity) {

    @Provides
    fun provideContext(): Context {
        return mActivity
    }

    @Provides
    fun provideActivity(): AppCompatActivity {
        return mActivity
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    fun provideLinearLayoutManager(activity: AppCompatActivity): LinearLayoutManager {
        return LinearLayoutManager(activity)
    }

    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider()

    @Provides
    @PerActivity
    fun provideMainPresenter(presenter: HomePresenter<HomeContracts.View>):
            HomeContracts.Presenter<HomeContracts.View> {
        return presenter
    }
}
