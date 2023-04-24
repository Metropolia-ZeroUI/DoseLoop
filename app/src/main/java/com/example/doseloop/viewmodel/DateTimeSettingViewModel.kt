package com.example.doseloop.viewmodel

import android.util.Log
import com.example.doseloop.comms.impl.Message
import java.time.LocalDate

class DateTimeSettingViewModel: AbstractViewModel() {
    fun onPopupConfirm(msg: Message, timeKey: String, time: String, dayKey: String, dateKey: String, isSwitchChecked: Boolean) {
        Log.d("POPUP_CONFIRM", "CONFIRMING!")
        val currentDate = LocalDate.now().toString()
        try {
            msgService.sendMessage(msg)
            // Save time state in SharedPreferences
            saveToPrefs(timeKey, time)
            Log.d("MESSAGE", "timeKey:$time")
            // Save switch state in SharedPreferences
            saveToPrefs(dayKey, isSwitchChecked)
            Log.d("MESSAGE", "dayKey:$isSwitchChecked")
            // Save switch state in SharedPreferences
            saveToPrefs(dateKey, currentDate)
            Log.d("MESSAGE", "dateKey:$currentDate")

            Log.d("MESSAGE_SEND", "Message OK")
        } catch(e: Exception) {
            Log.d("MESSAGE_SEND", "Send failed: $e")
        }
        msg.emptyPayload()
    }
}