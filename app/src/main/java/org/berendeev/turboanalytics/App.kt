package org.berendeev.turboanalytics

import android.app.Application
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    private fun initLogger() {
        Timber.plant(Timber.DebugTree())
    }
}