package com.example.doseloop.viewmodel

import android.util.Log
import com.example.doseloop.comms.impl.Message
import com.example.doseloop.util.DEVICE_LOCKED_STATE

class DeviceStatusViewModel: AbstractViewModel() {

    fun saveDeviceLockedState(deviceLocked: Boolean) {
        saveToPrefs(DEVICE_LOCKED_STATE, deviceLocked)
    }

    fun getLockedState() = getFromPrefs(DEVICE_LOCKED_STATE, false)

    fun getChanges(): String {
        return if (getFromPrefs(DEVICE_LOCKED_STATE, false)) "Älydosetin lukitus: pois" else "Älydosetin lukitus: päällä"
    }
}