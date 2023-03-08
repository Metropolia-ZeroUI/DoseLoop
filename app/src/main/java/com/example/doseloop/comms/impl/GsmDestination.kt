package com.example.doseloop.comms.impl

import com.example.doseloop.comms.Destination

class GsmDestination(private val phone: PhoneNumber) : Destination {
    override fun getAddress(): String = phone.getAddress()
}