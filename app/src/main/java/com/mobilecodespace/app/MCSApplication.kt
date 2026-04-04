package com.mobilecodespace.app

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MCSApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Globaler Error-Handler
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            Log.e("MCSApplication", "Uncaught exception in thread ${thread.name}", throwable)
            // Hier könnte man später eine Crash-Reporting-Library einbinden
        }
    }
}
