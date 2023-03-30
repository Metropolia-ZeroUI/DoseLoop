package com.example.doseloop.viewmodel

import android.util.Log
import com.example.doseloop.comms.impl.Message
import com.example.doseloop.util.*

/**
 * ViewModel for the DeviceStatusFragment and the related popup windows.
 */
class DeviceStatusViewModel: AbstractViewModel() {


    fun saveDeviceLockedState(deviceLocked: Boolean) {
        saveToPrefs(DEVICE_LOCKED_STATE, deviceLocked)
    }

    fun onPopupConfirmUserNumber(number: String) {
        try {
            msgService.sendMessage(Message.USER_PHONE_ADD.withPayload(number))
            saveToPrefs(DEVICE_USER_NUMBER, number)
            Log.d("MESSAGE_SEND", "Message OK")
        } catch(e: Exception) {
            Log.d("MESSAGE_SEND", "Send failed: $e")
        }
    }

    fun getLockedState() = getFromPrefs(DEVICE_LOCKED_STATE, false)

    fun getChanges(editTextNumber: String, deviceLocked: Boolean): String {
        var s = ""
        if (getFromPrefs(DEVICE_LOCKED_STATE, false) != deviceLocked) {
            s += if (deviceLocked) "Älydosetin lukitus päällä\n"
            else "Älydosetin lukitus pois\n"
        }
        if (getFromPrefs(DEVICE_USER_NUMBER, "") != editTextNumber) {
            s += "Älydosetin käyttäjän numero muokattu"
        }
        return s
    }
}