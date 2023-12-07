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
import kotlin.random.Random

@AndroidEntryPoint
class OrderService : Service(), CoroutineScope by MainScope() {

    companion object {
        private const val CHANNEL_ID = "Create Notification"

        private const val NOTIFICATION_ID = 123

        private const val ACTION_ORDER_DELIVERY = "ACTION_ORDER_DELIVERY"
        fun newIntent(context: Context) =
            Intent(context, OrderService::class.java).apply {
                action = ACTION_ORDER_DELIVERY
            }
    }

    override fun onBind(p0: Intent?): IBinder? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            val generatedTime = Random.nextLong(50,200)
            val notificationManager = NotificationManagerCompat.from(this)
            val expireTimeInMiliSeconds = Date().time + generatedTime*100
            startForeground(NOTIFICATION_ID,createNotification(expireTimeInMiliSeconds))
            val builder = NotificationCompat.Builder(this, CHANNEL_ID).apply {
                setContentTitle(getString(R.string.channel_name))
                setChronometerCountDown(true)
                setUsesChronometer(true)
                setShowWhen(true)
                setWhen(expireTimeInMiliSeconds)
                setProgress(100,0,false)
                setSmallIcon(com.example.data.R.drawable.ic_delivery_24)
            }
            if (it.action == ACTION_ORDER_DELIVERY) {
                launch {
                    val timer = object: CountDownTimer(generatedTime*100, 1000) {
                        @SuppressLint("MissingPermission")
                        override fun onTick(millisUntilFinished: Long) {
                            builder.setProgress(100,100-((millisUntilFinished/generatedTime).toInt()),false)
                            notificationManager.notify(NOTIFICATION_ID, builder.build())
                        }

                        @SuppressLint("MissingPermission")
                        override fun onFinish() {
                            builder.setContentText("Your products was delivered!")
                                .setUsesChronometer(false)
                            notificationManager.notify(NOTIFICATION_ID+1, builder.build())
                            stopSelf()
                        }
                    }
                    timer.start()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotification(expireTimeInMiliSeconds: Long) : Notification {
        createNotificationChannel()
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.channel_name))
            .setChronometerCountDown(true)
            .setUsesChronometer(true)
            .setShowWhen(true)
            .setWhen(expireTimeInMiliSeconds)
            .setSmallIcon(com.example.data.R.drawable.ic_delivery_24)
            .setProgress(100,0,false)
            .build()
    }

    private fun createNotificationChannel(){
        val channel = NotificationChannelCompat.Builder(
            CHANNEL_ID,
            NotificationManagerCompat.IMPORTANCE_MAX
        ).setName(getString(R.string.channel_name))
            .build()
        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }
    override fun onDestroy() {
        cancel()
        super.onDestroy()
    }
}