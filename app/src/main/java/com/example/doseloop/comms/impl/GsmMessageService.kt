package com.example.doseloop.comms.impl

import com.example.doseloop.comms.Destination
import com.example.doseloop.comms.MessageService

class GsmMessageService(val destination: Destination): MessageService {
    override fun sendMessage(msg: Message) {
        println("[${this::class.simpleName}] : [$msg] -- ${msg.encode()}")
    }
}