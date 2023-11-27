package com.example.deliveryapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.cameraPermissionRequest
import com.example.common.isPermissionGranted
import com.example.common.openPermissionSetting
import com.example.deliveryapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding by viewBinding(ActivityMainBinding::bind)

    private val cameraPermission = android.Manifest.permission.CAMERA

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
    }
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted)
                navHostFragment.navController.navigate(R.id.qrCodeFragment)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)


        val badge = binding.bottomNavigation.getOrCreateBadge(R.id.shop_nav_graph)
        badge.isVisible = true
        badge.number = 99
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.QRCode -> {
                    requestCameraAndStart()
                    true
                }
                else -> {
                    true
                }
            }
        }
        binding.buttonLocation.setOnClickListener {
            Toast.makeText(this, "Need api key for google maps", Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun requestCameraAndStart() {
        if (isPermissionGranted(cameraPermission)) {

            navHostFragment.navController.navigate(R.id.qrCodeFragment)
        } else
            requestCameraPermission()
    }

    private fun requestCameraPermission() {
        when {
            shouldShowRequestPermissionRationale(cameraPermission) -> {
                cameraPermissionRequest {
                    openPermissionSetting()
                }
            }

            else -> {
                requestPermissionLauncher.launch(cameraPermission)
            }
        }
    }
}