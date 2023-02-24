package com.example.doseloop.viewmodel

import androidx.lifecycle.ViewModel
import com.example.doseloop.repository.SharedPreferencesRepository

/**
 * Add common view model logic here, i.e. commonly used repositories, sharedprefs, etc.
 */
abstract class AbstractViewModel : ViewModel() {

    private val sharedPrefsRepository = SharedPreferencesRepository.instance

    fun <T> saveToPrefs(key: String, value: T) {
        when(value) {
            is Int -> sharedPrefsRepository.saveToPrefs(key, value as Int)
            is String -> sharedPrefsRepository.saveToPrefs(key, value as String)
            is Float -> sharedPrefsRepository.saveToPrefs(key, value as Float)
            is Boolean -> sharedPrefsRepository.saveToPrefs(key, value as Boolean)
            is Long -> sharedPrefsRepository.saveToPrefs(key, value as Long)
        }
    }

    fun getFromPrefs(key: String, default: String) = sharedPrefsRepository.getFromPrefs(key, default)
    fun getFromPrefs(key: String, default: Int) = sharedPrefsRepository.getFromPrefs(key, default)
    fun getFromPrefs(key: String, default: Boolean) = sharedPrefsRepository.getFromPrefs(key, default)
    fun getFromPrefs(key: String, default: Float) = sharedPrefsRepository.getFromPrefs(key, default)
    fun getFromPrefs(key: String, default: Long) = sharedPrefsRepository.getFromPrefs(key, default)
}