package com.mobilecodespace.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MCSApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Hier könnte später ein globaler Error-Handler oder Logging-Initialisierung erfolgen
    }
}
