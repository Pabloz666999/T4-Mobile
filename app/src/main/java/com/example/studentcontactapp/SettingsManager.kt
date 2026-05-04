package com.example.studentcontactapp.utils

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {

    private val PREF_NAME = "AppSettingsPrefs"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private const val KEY_DARK_MODE = "dark_mode"
        private const val KEY_FONT_SIZE = "font_size"
        private const val KEY_NOTIFICATION = "notification_enabled"
    }

    // --- DARK MODE ---
    fun setDarkMode(isDarkMode: Boolean) {
        editor.putBoolean(KEY_DARK_MODE, isDarkMode).apply()
    }

    fun isDarkMode(): Boolean {
        return sharedPreferences.getBoolean(KEY_DARK_MODE, false) // Default: false (Light Mode)
    }

    // --- FONT SIZE ---
    fun setFontSize(size: Int) {
        editor.putInt(KEY_FONT_SIZE, size).apply()
    }

    fun getFontSize(): Int {
        return sharedPreferences.getInt(KEY_FONT_SIZE, 14) // Default font size: 14
    }

    // --- NOTIFICATION ---
    fun setNotificationEnabled(isEnabled: Boolean) {
        editor.putBoolean(KEY_NOTIFICATION, isEnabled).apply()
    }

    fun isNotificationEnabled(): Boolean {
        return sharedPreferences.getBoolean(KEY_NOTIFICATION, true) // Default: true (Nyala)
    }
}