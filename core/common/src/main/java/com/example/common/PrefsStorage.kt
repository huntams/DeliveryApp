package com.example.common

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefsStorage @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    companion object {
        private const val PREFS_NAME = "Prefs"
        private const val ORDER_KEY = "ORDER_KEY"
    }

    private val prefs by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    var order: Long
        get() = prefs.getLong(ORDER_KEY, 0)
        set(value) {
            prefs.edit {
                putLong(ORDER_KEY, value)
            }
        }



}