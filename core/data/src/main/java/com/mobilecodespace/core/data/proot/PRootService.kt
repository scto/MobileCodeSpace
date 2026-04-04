package com.mobilecodespace.core.data.proot

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

/**
 * Android Service, um die Ubuntu-Instanz im Hintergrund stabil zu halten.
 */
class PRootService : Service() {

    private lateinit var prootManager: PRootManager
    private var prootProcess: Process? = null

    override fun onCreate() {
        super.onCreate()
        prootManager = PRootManager(this)
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Initialisierung und Start des Prozesses
        prootManager.installProotBinary()
        prootManager.setupRootfs()
        
        prootProcess = prootManager.startProot()
        
        return START_STICKY
    }

    private fun startForegroundService() {
        val channelId = "proot_service_channel"
        val channel = NotificationChannel(
            channelId,
            "PRoot Service",
            NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("MobileCodeSpace")
            .setContentText("Ubuntu Umgebung läuft im Hintergrund")
            .setSmallIcon(android.R.drawable.ic_menu_save) // Platzhalter Icon
            .build()

        startForeground(1, notification)
    }

    override fun onDestroy() {
        prootProcess?.destroy()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
