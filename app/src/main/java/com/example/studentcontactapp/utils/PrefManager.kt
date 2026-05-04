package com.example.studentcontactapp.utils

import android.content.Context
import android.content.SharedPreferences

class PrefManager(context: Context) {

    // Nama file SharedPreferences kita
    private val PREF_NAME = "StudentAppPrefs"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    // Kunci (Key) untuk menyimpan data
    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USERNAME = "username"
        private const val KEY_REMEMBER_ME = "remember_me"
    }

    // Fungsi untuk menyimpan sesi login
    fun saveLoginSession(username: String, isRemembered: Boolean) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putString(KEY_USERNAME, username)
        editor.putBoolean(KEY_REMEMBER_ME, isRemembered)
        editor.apply() // apply() menyimpan data secara asynchronous (background)
    }

    // Mengecek apakah user sudah login
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    // Mengambil username yang sedang login
    fun getUsername(): String? {
        return sharedPreferences.getString(KEY_USERNAME, "User")
    }

    // Mengecek apakah fitur remember me dicentang
    fun isRememberMe(): Boolean {
        return sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)
    }

    // Fungsi untuk logout (menghapus semua data sesi)
    fun logout() {
        editor.clear()
        editor.apply()
    }
}