package com.example.doseloop.viewmodel

import android.os.Parcelable
import android.util.Log
import android.widget.CompoundButton
import androidx.lifecycle.MutableLiveData
import com.example.doseloop.comms.impl.Message
import com.example.doseloop.util.*
import kotlinx.android.parcel.Parcelize

/**
 * Provides functionality for adding notification phone numbers and updating the notification status of the given numbers.
 */
@Parcelize
class PhoneNumberSettingViewModel: AbstractViewModel(), Parcelable {

    // TODO: Set these values according to prefs on init
    // TODO: check if something is not same in prefs when going away from the fragment, make popup for informing of stuff not saved
    var number1Notification = MutableLiveData(true)
    var number2Notification = MutableLiveData(true)
    var number3Notification = MutableLiveData(true)
    var number4Notification = MutableLiveData(true)
    var number5Notification = MutableLiveData(true)

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

    fun updateNotificationReceivers() {
        try {
            val msg = Message.FACTORY_ALARM_FOR_PHONES.withEmptyPayload().withPayload(getNotifyReceiversAsList())
            msgService.sendMessage(msg)
            saveNotifyStatusToPrefs()
            Log.d("MESSAGE_SEND", "Message OK")
        } catch(e: Exception) {
            Log.d("MESSAGE_SEND", "Send failed: $e")
        }
    }

    fun updateNumber1Notification(buttonView: CompoundButton, b: Boolean) {
        number1Notification.value = b
    }
    fun updateNumber2Notification(buttonView: CompoundButton, b: Boolean) {
        number2Notification.value = b
    }
    fun updateNumber3Notification(buttonView: CompoundButton, b: Boolean) {
        number3Notification.value = b
    }
    fun updateNumber4Notification(buttonView: CompoundButton, b: Boolean) {
        number4Notification.value = b
        Log.d("sendMessage", "updated n4 $b")
    }
    fun updateNumber5Notification(buttonView: CompoundButton, b: Boolean) {
        number5Notification.value = b
    }

    fun getNotifiedNumbersToString(): String {
        var r = ""
        this.getNotifyReceiversAsList().forEach {
            r = if (r.isEmpty()) it
            else "$r, $it"
        }
        Log.d("sendMessage", r)
        return r
    }

    fun getNotNotifiedNumbersToString(): String {
        var r = ""
        this.getNotNotifyReceiversAsList().forEach {
            r = if (r.isEmpty()) it
            else "$r, $it"
        }
        return r
    }

    private fun getNotifyReceiversAsList(): List<String> {
        val l = arrayListOf<String>()
        if (number1Notification.value == true) l.add("1")
        if (number2Notification.value == true) l.add("2")
        if (number3Notification.value == true) l.add("3")
        if (number4Notification.value == true) l.add("4")
        if (number5Notification.value == true) l.add("5")
        return l
    }

    private fun getNotNotifyReceiversAsList(): List<String> {
        val l = arrayListOf<String>()
        if (number1Notification.value == false) l.add("1")
        if (number2Notification.value == false) l.add("2")
        if (number3Notification.value == false) l.add("3")
        if (number4Notification.value == false) l.add("4")
        if (number5Notification.value == false) l.add("5")
        return l
    }

    private fun saveNotifyStatusToPrefs() {
        saveToPrefs(PHONE_NUMBER_1_NOTIFICATION, number1Notification.value)
        saveToPrefs(PHONE_NUMBER_2_NOTIFICATION, number2Notification.value)
        saveToPrefs(PHONE_NUMBER_3_NOTIFICATION, number3Notification.value)
        saveToPrefs(PHONE_NUMBER_4_NOTIFICATION, number4Notification.value)
        saveToPrefs(PHONE_NUMBER_5_NOTIFICATION, number5Notification.value)
    }
}