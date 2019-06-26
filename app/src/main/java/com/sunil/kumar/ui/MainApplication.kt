package com.sunil.kumar.ui

import android.app.Activity
import android.app.Application
import android.util.Log
import com.sunil.kumar.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.reactivex.plugins.RxJavaPlugins
import javax.inject.Inject

class MainApplication : Application(), HasActivityInjector {

    companion object {

        const val TAG = "MainApplication"
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): DispatchingAndroidInjector<Activity> = androidInjector

    override fun onCreate() {
        super.onCreate()
        setUpDaggerModule()

        RxJavaPlugins.setErrorHandler { e -> Log.d(TAG, "Error: " + e.message) }
    }

    private fun setUpDaggerModule() {

        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }
}