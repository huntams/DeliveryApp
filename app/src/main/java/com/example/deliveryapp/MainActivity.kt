package com.example.deliveryapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.deliveryapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)
    private val viewModel by viewModels<MainViewModel>()
    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
    }

    //private var data = false

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)

        viewModel.getOrders()
        viewModel.ordersLiveData.observe(this) { list ->

            val badge = binding.bottomNavigation.getOrCreateBadge(R.id.shop_nav_graph)
            badge.isVisible = true
            badge.number = list.last().productQuantity.sumOf {
                it.productQuantity.quantity
            }
        }

    }

    /*
    fun test() {
        viewModel.getOrders()
        data = true
    }

    private fun createNotification(): Notification {
        createNotificationChannel()
        return NotificationCompat.Builder(
            this,
            RandomProductPushNotificationService.NOTIFICATION_CHANNEL_ID
        )
            .setContentTitle(getString(com.example.shop.R.string.channel_name))
            .setSmallIcon(com.example.data.R.drawable.ic_delivery_24)
            .build()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannelCompat.Builder(
            RandomProductPushNotificationService.NOTIFICATION_CHANNEL_ID,
            NotificationManagerCompat.IMPORTANCE_MAX,
        ).setName(getString(com.example.shop.R.string.channel_name))
            .setLightsEnabled(true)
            .setLightColor(Color.RED)
            .setVibrationEnabled(true)
            .setVibrationPattern(longArrayOf(0, 1000, 500, 1000))
            .build()
        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }
     */
}