package com.example.doseloop.comms

import com.example.doseloop.comms.impl.Message

interface MessageService {
    fun sendMessage(msg: Message)
}