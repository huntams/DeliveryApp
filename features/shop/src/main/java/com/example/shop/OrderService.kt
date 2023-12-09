package com.example.shop

import android.annotation.SuppressLint
import android.app.Notification
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.Date

@AndroidEntryPoint
class OrderService : Service(), CoroutineScope by MainScope() {

    companion object {
        private const val CHANNEL_ID = "Create Notification"
        private var timeMilliseconds: Long = 0

        private const val NOTIFICATION_ID = 123

        private const val ACTION_ORDER_DELIVERY = "ACTION_ORDER_DELIVERY"
        fun newIntent(time: Long, context: Context): Intent {
            timeMilliseconds = time
            return Intent(context, OrderService::class.java).apply {
                action = ACTION_ORDER_DELIVERY
            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            val context = this
            val notificationManager = NotificationManagerCompat.from(this)
            val expireTimeInMiliSeconds = timeMilliseconds - Date().time
            startForeground(NOTIFICATION_ID, createNotification(timeMilliseconds))
            val builder = createCompat(timeMilliseconds)

            if (it.action == ACTION_ORDER_DELIVERY) {
                launch {
                    val timer = object : CountDownTimer(expireTimeInMiliSeconds, 1000) {
                        @SuppressLint("MissingPermission")
                        override fun onTick(millisUntilFinished: Long) {
                            builder.setProgress(
                                100,
                                100 - ((millisUntilFinished / (expireTimeInMiliSeconds / 100)).toInt()),
                                false
                            )
                            notificationManager.notify(NOTIFICATION_ID, builder.build())
                        }

                        @SuppressLint("MissingPermission")
                        override fun onFinish() {
                            val build = NotificationCompat.Builder(context, CHANNEL_ID)
                                .setContentTitle(getString(R.string.channel_name))
                                .setContentText("Your products was delivered!")
                                .setUsesChronometer(false)
                                .setSmallIcon(com.example.data.R.drawable.ic_delivery_24)
                            notificationManager.notify(NOTIFICATION_ID + 1, build.build())
                            stopSelf()
                        }
                    }
                    timer.start()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createCompat(expireTimeInMiliSeconds: Long): NotificationCompat.Builder {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.channel_name))
            .setChronometerCountDown(true)
            .setUsesChronometer(true)
            .setShowWhen(true)
            .setWhen(expireTimeInMiliSeconds)
            .setProgress(100, 0, false)
            .setSmallIcon(com.example.data.R.drawable.ic_delivery_24)
    }

    private fun createNotification(expireTimeInMiliSeconds: Long): Notification {
        createNotificationChannel()
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.channel_name))
            .setChronometerCountDown(true)
            .setUsesChronometer(true)
            .setShowWhen(true)
            .setWhen(expireTimeInMiliSeconds)
            .setSmallIcon(com.example.data.R.drawable.ic_delivery_24)
            .setProgress(100, 0, false)
            .build()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannelCompat.Builder(
            CHANNEL_ID,
            NotificationManagerCompat.IMPORTANCE_LOW
        ).setName(getString(R.string.channel_name))
            .build()
        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }

    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }
}