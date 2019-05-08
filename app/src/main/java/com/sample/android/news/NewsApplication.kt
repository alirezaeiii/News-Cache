package com.sample.android.news

import android.app.Application
import timber.log.Timber

/**
 * Override application to setup background work via WorkManager
 */
class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Set up Timber
        Timber.plant(Timber.DebugTree())
    }
}