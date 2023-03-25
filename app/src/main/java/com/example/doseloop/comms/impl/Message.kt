package com.example.doseloop.comms.impl

import com.example.doseloop.comms.util.TimeOfDay24

/**
 * Buildable message that is able to encode it's paylod
 * to SMS format.
 */
@Suppress("SpellCheckingInspection")
enum class Message(private val code: Int, private var payload: String = "")  {
    /**
     * Text alert that appears on device when medications
     * are not taken on time.
     */
    ALARM_MISSED_MEDS(120),
    /**
     * Text alert that appears on device when alarm button
     * is pressed.
     */
    ALARM_BUTTON_PRESS(121),
    /**
     * Text alert that appears when Firealarm or leak detection
     * is triggered.
     */
    ALARM_FIRE_WATER(122),
    /**
     * Text alert that appears when food is not eaten on
     * time.
     */
    ALARM_MISSED_FOOD(123),

    /**
     * Set following phone number as phone number 1
     */
    PHONE_SET_1(110),
    /**
     * Set following phone number as phone number 2
     */
    PHONE_SET_2(111),
    /**
     * Set following phone number as phone number 3
     */
    PHONE_SET_3(112),
    /**
     * Set following phone number as phone number 4
     */
    PHONE_SET_4(113),
    /**
     * Set following phone number as phone number 5
     */
    PHONE_SET_5(114),

    /**
     * Set which phones are alerted on possible alarm.
     */
    ALARM_FOR_PHONES(40),

    /**
     * Set time of day for medication 1, and frequency
     * (every day / every other day)
     */
    TIME_FOR_MEDS_1(51),
    /**
     * Set time of day for medication 2, and frequency
     * (every day / every other day)
     */
    TIME_FOR_MEDS_2(52),
    /**
     * Set time of day for medication 3, and frequency
     * (every day / every other day)
     */
    TIME_FOR_MEDS_3(53),
    /**
     * Set time of day for medication 4, and frequency
     * (every day / every other day)
     */
    TIME_FOR_MEDS_4(54),
    /**
     * Set time of day for medication 5, and frequency
     * (every day / every other day)
     */
    TIME_FOR_MEDS_5(55),
    /**
     * Set time of day for medication 6, and frequency
     * (every day / every other day)
     */
    TIME_FOR_MEDS_6(56),

    /**
     * Lock device controls.
     */
    LOCK_DEVICE(68, payload = "lock"),
    /**
     * Unlock device controls
     */
    UNLOCK_DEVICE(69, payload = "open"),

    /**
     * Add device user's phone number to the device.
     */
    USER_PHONE_ADD(15),
    /**
     * Remove device user's phone number from decice.
     */
    USER_PHONE_REMOVE(15, payload = "+358"),
    /**
     * Disable alerts to user's phone.
     */
    USER_PHONE_DISABLE(17),
    /**
     * Send reminders to user's phone with SMS.
     */
    USER_PHONE_MEDS_REMINDER_SMS(18),
    /**
     * Send reminders to user's phone with phone call.
     */
    USER_PHONE_MEDS_REMINDER_CALL(19),

    /**
     * Factory defaults
     */
    FACTORY_ALARM_FOR_PHONES(40, payload = "1,2,3,4,5"),
    FACTORY_ALARM_MISSED_MEDS(120, payload = "Lääkkeitä ei ole otettu"),
    FACTORY_ALARM_BUTTON_PRESS(121, payload = "Dosetti muistuttajan hälytys viesti"),
    FACTORY_ALARM_FIRE_WATER(122, payload = "Palovaroitin hälytys"),
    FACTORY_ALARM_MISSED_FOOD(123, payload = "Ruokaa ei ole syöty määräajassa"),
    FACTORY_PHONE_SET_1(110, payload = "+358..."), // TODO: This is still a ??
    FACTORY_PHONE_SET_2(111, payload = "+358..."),
    FACTORY_PHONE_SET_3(112, payload = "+358..."),
    FACTORY_PHONE_SET_4(113, payload = "+358..."),
    FACTORY_PHONE_SET_5(114, payload = "+358..."),

    /**
     *  Query phone numbers associated with the device.
     */
    ADMIN_PHONE_NUMBER_QUERY(31),
    /**
     *  Query SMS list associated with the device.
     */
    ADMIN_SMS_QUERY(32),
    /**
     * Set device time
     */
    ADMIN_SET_TIME(37),
    /**
     * Query medications from device.
     */
    ADMIN_LIST_MEDS(38),
    /**
     * Query signal strength from the device.
     */
    ADMIN_SIGNAL_STRENGTH(30),
    /**
     * Query battery life (%) from the device.
     */
    ADMIN_BATTERY_LIFE(36),
    /**
     * Query last three door opening times.
     */
    ADMIN_DOOR_OPENING_TIMES(39);

    /**
     * Sets multiple objects as a payload for this message.
     * Objects' String representations are separated by comma.
     *
     * @param payloads Iterable<String>
     * @return this object
     */
    fun withPayload(payloads: Iterable<Any>): Message {
        payloads.forEach {
            if (this.payload.isEmpty()) this.withPayload(it)
            else this.and(it)
        }
        return this;
    }

    /**
     * Set integer as a payload for this message.
     *
     * @param payload Int
     * @return this object
     */
    fun withPayload(payload: Any): Message {
        this.payload += "$payload"
        return this
    }

    /**
     * Append Object to message payload, separated by comma.
     *
     * @param payload Value
     * @return this object
     */
    fun and(payload: Any): Message {
        if (this.payload.isEmpty()) {
            throw IllegalStateException("Empty base payload.")
        }
        this.payload = "${this.payload},$payload"
        return this
    }

    fun encode(): String = if (this.payload.isNotEmpty()) "!$code=$payload!" else "!$code!"

    fun emptyPayload() {
        this.payload = ""
    }

    override fun toString(): String = "${this::class.simpleName} - $name";

    companion object {
        const val ALARM_DISABLE = ""
        const val MEDS_EVERY_DAY = 1
        const val MEDS_EVERY_OTHER_DAY = 0
    }
}