package com.sunil.kumar.di

import android.content.Context
import com.sunil.kumar.ui.MainApplication
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    companion object {
        const val MAIN_THREAD_SCHEDULER = "MAIN_THREAD_SCHEDULER"
        const val BACKGROUND_SCHEDULER = "BACKGROUND_SCHEDULER"
    }

    @Provides
    fun provideContext(mainApplication: MainApplication): Context {
        return mainApplication.applicationContext
    }

    @Provides
    @Singleton
    @Named(MAIN_THREAD_SCHEDULER)
    fun provideMainThreadScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    @Singleton
    @Named(BACKGROUND_SCHEDULER)
    fun provideBackGroundThreadScheduler(): Scheduler {
        return Schedulers.io()
    }
}