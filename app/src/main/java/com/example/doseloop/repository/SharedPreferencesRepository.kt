package com.example.doseloop.repository

import android.content.Context
import com.example.doseloop.DoseLoopApplication
import com.example.doseloop.util.PREF_KEY

/**
 * Provides access to SharedPreferences.
 */
class SharedPreferencesRepository {

    private val app = DoseLoopApplication.instance
    private val prefs = app.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    companion object {
        val instance: SharedPreferencesRepository = SharedPreferencesRepository()
    }

    fun saveToPrefs(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }
    fun saveToPrefs(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }
    fun saveToPrefs(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }
    fun saveToPrefs(key: String, value: Float) {
        editor.putFloat(key, value)
        editor.apply()
    }
    fun saveToPrefs(key: String, value: Long) {
        editor.putLong(key, value)
        editor.apply()
    }

    fun getFromPrefs(key: String, default: String) = prefs.getString(key, default)
    fun getFromPrefs(key: String, default: Int) = prefs.getInt(key, default)
    fun getFromPrefs(key: String, default: Boolean) = prefs.getBoolean(key, default)
    fun getFromPrefs(key: String, default: Float) = prefs.getFloat(key, default)
    fun getFromPrefs(key: String, default: Long) = prefs.getLong(key, default)

}