package com.example.doseloop.viewmodel

import android.util.Log
import android.widget.CompoundButton
import androidx.lifecycle.MutableLiveData
import com.example.doseloop.comms.impl.Message
import com.example.doseloop.util.DEVICE_LOCKED_STATE

class DeviceStatusViewModel: AbstractViewModel() {

    // TODO: Add Locking and unlocking functionality
    // TODO: Add Setting sms/call alert for the device user
    val deviceLockedState = MutableLiveData(false)

    fun saveDeviceLockedState() {
        try {
            val deviceLocked = getFromPrefs(DEVICE_LOCKED_STATE, false)
            var msg = if (deviceLocked) Message.UNLOCK_DEVICE else Message.LOCK_DEVICE
            msgService.sendMessage(msg)
         //  TODO: Save to prefs
            Log.d("MESSAGE_SEND", "Message OK")
        } catch(e: Exception) {
            Log.d("MESSAGE_SEND", "Send failed: $e")
        }
    }

    fun toggleDeviceLockedState(buttonView: CompoundButton, b: Boolean) {
        deviceLockedState.value = b
    }
}