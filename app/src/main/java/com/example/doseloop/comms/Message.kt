package com.example.doseloop.comms

interface MessageType {
    fun sms(): String;
    fun json(): String;
}

enum class Message(private val code: Int, private var payload: String = "") :
    MessageType {
    ALARM_MISSED_MEDS(120),
    ALARM_BUTTON_PRESS(121),
    ALARM_FIRE_WATER(122),
    ALARM_MISSED_FOOD(123),

    PHONE_SET_1(110),
    PHONE_SET_2(111),
    PHONE_SET_3(112),
    PHONE_SET_4(113),
    PHONE_SET_5(114);

    override fun sms(): String = "!${code}=${payload}!"
    override fun json(): String = "{ \"code\": $code, \"payload\": \"${payload}\" }" // :)
    fun withPayload(payload: String): Message {
        this.payload = payload
        return this;
    }
    companion object {
        const val DISABLE = ""
    }
}

class PhoneNumber(private val number: String) : Destination {
    private fun getFinnishPhone(): String {
        // TODO: DO SOME REAL STUFF
        return "+358${number.substring(1, number.length)}"
    }
    fun normalize(): String = getFinnishPhone()
    override fun getAddress(): String = normalize()
}


interface Destination {
    fun getAddress(): String
}

class GsmDestination(private val phone: PhoneNumber) : Destination {
    override fun getAddress(): String = phone.getAddress()
}

class HttpDestination(private val url: String): Destination {
    override fun getAddress(): String {
        return url
    }
}

interface MessageService {
    fun sendMessage(msg: MessageType)
}

class GsmMessageService(val destination: Destination): MessageService {
    override fun sendMessage(msg: MessageType) {
        println("[${this::class.simpleName}] : [${msg::class.simpleName}] -- ${msg.sms()}")
    }
}

class RestMessageService(val destination: Destination): MessageService {
    override fun sendMessage(msg: MessageType) {
        println("[${this::class.simpleName}] : [${msg::class.simpleName}] -- ${msg.json()}")
    }
}


fun main() {
    val alarm1 = Message.ALARM_BUTTON_PRESS.withPayload("You pressed the button")
    val alarm2 = Message.ALARM_MISSED_FOOD.withPayload("Eat yer food, grandma")
    val alarm3 = Message.ALARM_MISSED_MEDS.withPayload(Message.DISABLE)

    val phone1 = Message.PHONE_SET_1.withPayload(PhoneNumber("0204521350").normalize())
    val phone2 = Message.PHONE_SET_2.withPayload(PhoneNumber("0504525780").normalize())

    val msgService = GsmMessageService(GsmDestination(PhoneNumber("05048569823")))
    msgService.sendMessage(alarm1)
    msgService.sendMessage(alarm2)
    msgService.sendMessage(alarm3)

    msgService.sendMessage(phone1)
    msgService.sendMessage(phone2)

    println()

    val restService = RestMessageService(HttpDestination("http://not.my.url.com"))
    restService.sendMessage(alarm1)
    restService.sendMessage(alarm2)
    restService.sendMessage(alarm3)

    restService.sendMessage(phone1)
    restService.sendMessage(phone2)

    /* OUTPUT
    [GsmMessageService] : [AlarmMessage] -- !121=You pressed the button!
    [GsmMessageService] : [AlarmMessage] -- !123=Eat yer food, grandma!
    [GsmMessageService] : [AlarmMessage] -- !120=!
    [GsmMessageService] : [PhoneNumberMessage] -- !110=+358204521350!
    [GsmMessageService] : [PhoneNumberMessage] -- !111=+358504525780!

    [RestMessageService] : [AlarmMessage] -- { "code": 121, "payload": "You pressed the button" }
    [RestMessageService] : [AlarmMessage] -- { "code": 123, "payload": "Eat yer food, grandma" }
    [RestMessageService] : [AlarmMessage] -- { "code": 120, "payload": "" }
    [RestMessageService] : [PhoneNumberMessage] -- { "code": 110, "payload": "+358204521350" }
    [RestMessageService] : [PhoneNumberMessage] -- { "code": 111, "payload": "+358504525780" }
    */
}

