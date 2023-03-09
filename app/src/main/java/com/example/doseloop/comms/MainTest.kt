package com.example.doseloop.comms

import com.example.doseloop.comms.impl.GsmDestination
import com.example.doseloop.comms.impl.GsmMessageService
import com.example.doseloop.comms.impl.Message
import com.example.doseloop.comms.impl.PhoneNumber
import com.example.doseloop.comms.util.TimeOfDay24

fun main() {
    val alarm1 = Message.ALARM_BUTTON_PRESS.withPayload("You pressed the button")
    val alarm2 = Message.ALARM_MISSED_FOOD.withPayload("Eat yer food, grandma")
    val alarm3 = Message.ALARM_MISSED_MEDS.withPayload(Message.ALARM_DISABLE)

    val phone1 = Message.PHONE_SET_1.withPayload(PhoneNumber("0204521350"))
    val phone2 = Message.PHONE_SET_2.withPayload(PhoneNumber("0504525780"))

    val phoneAlarm = Message.ALARM_FOR_PHONES.withPayload(listOf(1, 2, 3)).and(4).and(5);

    val meds1 = Message.TIME_FOR_MEDS_1
        .withPayload(TimeOfDay24(11, 30))
        .and(Message.MEDS_EVERY_DAY)

    val meds2 = Message.TIME_FOR_MEDS_2
        .withPayload(TimeOfDay24.EMPTY)
        .and(Message.MEDS_EVERY_DAY)

    val meds3 = Message.TIME_FOR_MEDS_3
        .withPayload(TimeOfDay24(20,30))
        .and(Message.MEDS_EVERY_OTHER_DAY)

    val lock = Message.LOCK_DEVICE
    val unlock = Message.UNLOCK_DEVICE

    val addUserPhone = Message.USER_PHONE_ADD.withPayload(PhoneNumber("0504962823"))
    val removeUserPhone = Message.USER_PHONE_REMOVE
    val disableUserPhone = Message.USER_PHONE_DISABLE
    val smsReminderToUserPhone = Message.USER_PHONE_MEDS_REMINDER_SMS
    val callReminderToUserPhone = Message.USER_PHONE_MEDS_REMINDER_CALL

    val msgService = GsmMessageService(GsmDestination(PhoneNumber("05048569823")))
    msgService.sendMessage(alarm1)
    msgService.sendMessage(alarm2)
    msgService.sendMessage(alarm3)

    msgService.sendMessage(phone1)
    msgService.sendMessage(phone2)

    msgService.sendMessage(phoneAlarm)

    msgService.sendMessage(meds1)
    msgService.sendMessage(meds2)
    msgService.sendMessage(meds3)

    msgService.sendMessage(lock)
    msgService.sendMessage(unlock)

    msgService.sendMessage(addUserPhone)
    msgService.sendMessage(removeUserPhone)
    msgService.sendMessage(disableUserPhone)
    msgService.sendMessage(smsReminderToUserPhone)
    msgService.sendMessage(callReminderToUserPhone)
}

