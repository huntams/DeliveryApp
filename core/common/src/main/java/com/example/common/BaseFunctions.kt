package com.example.common

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun Bitmap.convertToByteArray(): ByteArray = ByteArrayOutputStream().apply {
    compress(Bitmap.CompressFormat.JPEG, 50, this)
}.toByteArray()