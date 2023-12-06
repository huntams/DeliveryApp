package com.example.common

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream
import java.text.NumberFormat

fun Bitmap.convertToByteArray(): ByteArray = ByteArrayOutputStream().apply {
    compress(Bitmap.CompressFormat.JPEG, 50, this)
}.toByteArray()

fun Context.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

inline fun Context.cameraPermissionRequest(crossinline positive: () -> Unit) {
    AlertDialog.Builder(this).setTitle("Camera permission required")
        .setMessage("Without accessing the camera it is not possible to SCAN QR Codes...")
        .setPositiveButton("Allow camera") { dialog, which ->
            positive.invoke()
        }.setNegativeButton(" Cancel") { dialog, which -> }.show()
}

fun getFormattedPrice(price: Int): String = NumberFormat.getCurrencyInstance().format(price)
fun Context.openPermissionSetting(){
    Intent(ACTION_APPLICATION_DETAILS_SETTINGS).also {
        it.data = Uri.fromParts("package",packageName,null)
        startActivity(it)
    }
}