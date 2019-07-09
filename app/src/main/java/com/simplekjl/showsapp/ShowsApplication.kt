package com.simplekjl.showsapp

import android.app.Application
import com.simplekjl.showsapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber


class ShowsApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        //start koin
        startKoin {
            androidLogger()
            androidContext(this@ShowsApplication)
            modules(listOf(appModule))
        }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}