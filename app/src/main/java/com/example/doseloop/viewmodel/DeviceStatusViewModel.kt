package com.example.doseloop.viewmodel

import android.util.Log
import com.example.doseloop.comms.impl.Message
import com.example.doseloop.util.DEVICE_LOCKED_STATE

class DeviceStatusViewModel: AbstractViewModel() {

    // TODO: Add Setting sms/call alert for the device user

    fun saveDeviceLockedState(deviceLocked: Boolean) {
        try {
            val msg = if (deviceLocked) Message.UNLOCK_DEVICE else Message.LOCK_DEVICE
            msgService.sendMessage(msg)
            saveToPrefs(DEVICE_LOCKED_STATE, deviceLocked)
            Log.d("MESSAGE_SEND", "Message OK")
        } catch(e: Exception) {
            Log.d("MESSAGE_SEND", "Send failed: $e")
        }
    }

    fun getLockedState() = getFromPrefs(DEVICE_LOCKED_STATE, false)

    fun getChanges(): String {
        return if (getFromPrefs(DEVICE_LOCKED_STATE, false)) "Älydosetin lukitus: pois" else "Älydosetin lukitus: päällä"
    }
}