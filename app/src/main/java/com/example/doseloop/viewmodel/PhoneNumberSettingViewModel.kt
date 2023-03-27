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

    var number1Notification = MutableLiveData(true)
    var number2Notification = MutableLiveData(true)
    var number3Notification = MutableLiveData(true)
    var number4Notification = MutableLiveData(true)
    var number5Notification = MutableLiveData(true)

    init {
        number1Notification.value = getFromPrefs(PHONE_NUMBER_1_NOTIFICATION, true)
        number2Notification.value = getFromPrefs(PHONE_NUMBER_2_NOTIFICATION, true)
        number3Notification.value = getFromPrefs(PHONE_NUMBER_3_NOTIFICATION, true)
        number4Notification.value = getFromPrefs(PHONE_NUMBER_4_NOTIFICATION, true)
        number5Notification.value = getFromPrefs(PHONE_NUMBER_5_NOTIFICATION, true)
    }

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

    fun updateNotificationReceiversToDevice() {
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

    fun updateNotifyReceivers(n1: Boolean?, n2: Boolean?, n3: Boolean?, n4: Boolean?, n5: Boolean?) {
        this.number1Notification.value = n1 ?: this.number1Notification.value
        this.number2Notification.value = n2 ?: this.number2Notification.value
        this.number3Notification.value = n3 ?: this.number3Notification.value
        this.number4Notification.value = n4 ?: this.number4Notification.value
        this.number5Notification.value = n5 ?: this.number5Notification.value
    }

    fun getUnsavedChanges(number1: String = "", number2: String = "", number3: String = "",
                          number4: String = "", number5: String = "",): String {
        var s = ""
        if (number1Notification.value !=
            getFromPrefs(PHONE_NUMBER_1_NOTIFICATION, number1Notification.value!!)) s += "1. Numeron ilmoitus\n"
        if (number2Notification.value !=
            getFromPrefs(PHONE_NUMBER_2_NOTIFICATION, number2Notification.value!!)) s += "2. Numeron ilmoitus\n"
        if (number3Notification.value !=
            getFromPrefs(PHONE_NUMBER_3_NOTIFICATION, number3Notification.value!!)) s += "3. Numeron ilmoitus\n"
        if (number4Notification.value !=
            getFromPrefs(PHONE_NUMBER_4_NOTIFICATION, number4Notification.value!!)) s += "4. Numeron ilmoitus\n"
        if (number5Notification.value !=
            getFromPrefs(PHONE_NUMBER_5_NOTIFICATION, number5Notification.value!!)) s += "5. Numeron ilmoitus\n"

        if (number1 != getFromPrefs(PHONE_NUMBER_1, "")) s += "1. Numero muokattu\n"
        if (number2 != getFromPrefs(PHONE_NUMBER_2, "")) s += "2. Numero muokattu\n"
        if (number3 != getFromPrefs(PHONE_NUMBER_3, "")) s += "3. Numero muokattu\n"
        if (number4 != getFromPrefs(PHONE_NUMBER_4, "")) s += "4. Numero muokattu\n"
        if (number5 != getFromPrefs(PHONE_NUMBER_5, "")) s += "5. Numero muokattu\n"

        return s
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