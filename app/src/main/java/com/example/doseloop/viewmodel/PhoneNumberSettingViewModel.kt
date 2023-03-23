package com.example.doseloop.viewmodel

import android.util.Log
import com.example.doseloop.comms.impl.Message

class PhoneNumberSettingViewModel: AbstractViewModel() {

    fun onPopupConfirm(msg: Message, numberKey: String, number: String) {
        try {
            msgService.sendMessage(msg)
            saveToPrefs(numberKey, number)
            Log.d("MESSAGE_SEND", "Message OK")
        } catch(e: Exception) {
            Log.d("MESSAGE_SEND", "Send failed: $e")
        }
        msg.emptyPayload()
    }
}