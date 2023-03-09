package com.example.doseloop.comms.impl

import com.example.doseloop.comms.Destination

class PhoneNumber(private val number: String) : Destination {
    private fun getFinnishPhone(): String {
        // TODO: DO SOME REAL STUFF
        return "+358${number.substring(1, number.length)}"
    }
    fun normalize(): String = getFinnishPhone()
    override fun getAddress(): String = normalize()

    override fun toString(): String {
        return normalize()
    }
}