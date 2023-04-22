package com.example.doseloop.viewmodel

import android.util.Log
import androidx.navigation.findNavController
import com.example.doseloop.R
import com.example.doseloop.comms.impl.Message
import com.example.doseloop.util.*

/**
 * ViewModel for the DeviceStatusFragment and the related popup windows.
 */
class DeviceStatusViewModel: AbstractViewModel() {

    fun saveDeviceNumber(phoneNumber: String) {
        saveToPrefs(DEVICE_PHONE_NUMBER, phoneNumber)
    }
    fun saveDeviceLockedState(deviceLocked: Boolean) {
        try {
            msgService.sendMessage(if (deviceLocked) Message.LOCK_DEVICE else Message.UNLOCK_DEVICE)
            saveToPrefs(DEVICE_LOCKED_STATE, deviceLocked)
            Log.d("MESSAGE_SEND", "Message OK")
        } catch(e: Exception) {
            Log.d("MESSAGE_SEND", "Send failed: $e")
        }

    }

    fun onPopupConfirmUserNumber(msg: Message, payload: String, prefKey: String) {
        try {
            msgService.sendMessage(msg)
            saveToPrefs(prefKey, payload)
            Log.d("MESSAGE_SEND", "Message OK")
        } catch(e: Exception) {
            Log.d("MESSAGE_SEND", "Send failed: $e")
        }
    }

    fun getLockedState() = getFromPrefs(DEVICE_LOCKED_STATE, false)

    fun getChanges(editTextNumber: String, deviceLocked: Boolean, deviceEditTextNumber: String): String {
        var s = ""
        if (getFromPrefs(DEVICE_LOCKED_STATE, false) != deviceLocked) {
            s += if (deviceLocked) "Älydosetin lukitus päällä\n"
            else "Älydosetin lukitus pois\n"
        }
        if (getFromPrefs(DEVICE_USER_NUMBER, "") != editTextNumber) {
            s += "Älydosetin käyttäjän numero muokattu\n"
        }
        if (getFromPrefs(DEVICE_PHONE_NUMBER, "") != deviceEditTextNumber) {
            s += "Älydosetin numero muokattu"
        }
        return s
    }
}