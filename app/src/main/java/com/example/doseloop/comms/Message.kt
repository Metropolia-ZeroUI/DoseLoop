package com.example.doseloop.comms

interface MessageType<T : MessageType<T>> {
    var payload: String
    val code: Int

    fun sms(): String = "!${code}=${payload}!"
    fun json(): String = "{ \"code\": $code, \"payload\": \"${payload}\" }" // :)

    @Suppress("UNCHECKED_CAST")
    fun withPayload(payload: String): T {
        this.payload = payload
        return this as T;
    }
}

object Payload {
    const val DISABLE_FEATURE = ""
}

enum class AlarmMessage(override val code: Int, override var payload: String = "") :
    MessageType<AlarmMessage> {
    MISSED_MEDS(120),
    BUTTON_PRESS(121),
    FIRE_WATER_ALARM(122),
    MISSED_FOOD(123);

    val DISABLE = ""
}


enum class PhoneNumberMessage(override val code: Int, override var payload: String = "") :
    MessageType<PhoneNumberMessage> {
    SET_PHONE_1(110),
    SET_PHONE_2(111),
    SET_PHONE_3(112),
    SET_PHONE_4(113),
    SET_PHONE_5(114);
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
    fun<T: MessageType<T>> sendMessage(msg: MessageType<T>)
}

class GsmMessageService(val destination: Destination): MessageService {
    override fun <T : MessageType<T>> sendMessage(msg: MessageType<T>) {
        println("[${this::class.simpleName}] : [${msg::class.simpleName}] -- ${msg.sms()}")
    }
}

class RestMessageService(val destination: Destination): MessageService {
    override fun <T : MessageType<T>> sendMessage(msg: MessageType<T>) {
        println("[${this::class.simpleName}] : [${msg::class.simpleName}] -- ${msg.json()}")
    }
}



fun main() {
    val alarm1 = AlarmMessage.BUTTON_PRESS.withPayload("You pressed the button")
    val alarm2 = AlarmMessage.MISSED_FOOD.withPayload("Eat yer food, grandma")
    val alarm3 = AlarmMessage.MISSED_MEDS.withPayload(Payload.DISABLE_FEATURE)
    val phone1 = PhoneNumberMessage.SET_PHONE_1.withPayload(PhoneNumber("0204521350").normalize())
    val phone2 = PhoneNumberMessage.SET_PHONE_2.withPayload(PhoneNumber("0504525780").normalize())

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

