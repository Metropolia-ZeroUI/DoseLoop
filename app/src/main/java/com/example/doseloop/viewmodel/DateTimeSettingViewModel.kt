package com.example.doseloop.viewmodel

import android.util.Log
import com.example.doseloop.comms.impl.Message

class DateTimeSettingViewModel: AbstractViewModel() {
    fun onPopupConfirm(msg: Message, timeKey: String, time: String, dayKey: String, isSwitchChecked: Boolean) {
        Log.d("POPUP_CONFIRM", "CONFIRMING!")
        try {
            msgService.sendMessage(msg)
            // Save time state in SharedPreferences
            saveToPrefs(timeKey, time)
            // Save switch state in SharedPreferences
            saveToPrefs(dayKey, isSwitchChecked)
            Log.d("MESSAGE_SEND", "Message OK")
        } catch(e: Exception) {
            Log.d("MESSAGE_SEND", "Send failed: $e")
        }
        msg.emptyPayload()
    }
}