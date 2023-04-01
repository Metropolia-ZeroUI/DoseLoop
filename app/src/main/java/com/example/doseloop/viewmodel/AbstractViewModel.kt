package com.example.doseloop.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.doseloop.DoseLoopApplication
import com.example.doseloop.comms.impl.Message
import com.example.doseloop.comms.impl.PhoneNumber
import com.example.doseloop.comms.impl.SmsMessageService
import com.example.doseloop.repository.SharedPreferencesRepository
import com.example.doseloop.util.DEVICE_PHONE_NUMBER

/**
 * Common view model logic, i.e. commonly used repositories, sharedprefs, etc.
 */
abstract class AbstractViewModel : ViewModel() {

    private val sharedPrefsRepository = SharedPreferencesRepository.instance
    val msgService = SmsMessageService(
        PhoneNumber(getFromPrefs(DEVICE_PHONE_NUMBER, "") ?: ""),
        DoseLoopApplication.instance)

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

    fun onPopupConfirm(msg: Message) {
        try {
            msgService.sendMessage(msg)
            Log.d("MESSAGE_SEND", "Message OK")
        } catch(e: Exception) {
            Log.d("MESSAGE_SEND", "Send failed: $e")
        }
        msg.emptyPayload()
    }
}