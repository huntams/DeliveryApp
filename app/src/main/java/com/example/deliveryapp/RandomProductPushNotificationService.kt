package com.example.deliveryapp

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.shop.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class RandomProductPushNotificationService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }


    companion object {

        const val NOTIFICATION_CHANNEL_ID = "PUSH_NOTIFICATION"
    }

    override fun onMessageReceived(message: RemoteMessage) {

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = createNotification()
        notificationManager.notify(1000, notification)
    }

    private fun createNotification(): Notification {
        createNotificationChannel()
        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.channel_name))
            .setSmallIcon(com.example.data.R.drawable.ic_delivery_24)
            .build()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannelCompat.Builder(
            NOTIFICATION_CHANNEL_ID,
            NotificationManagerCompat.IMPORTANCE_MAX,
        ).setName(getString(R.string.channel_name))
            .setLightsEnabled(true)
            .setLightColor(Color.RED)
            .setVibrationEnabled(true)
            .setVibrationPattern(longArrayOf(0, 1000, 500, 1000))
            .build()
        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }
}
