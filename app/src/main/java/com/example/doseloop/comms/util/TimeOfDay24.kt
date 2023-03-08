package com.example.doseloop.comms.util

class TimeOfDay24(private val hours: Int, private val minutes: Int) {
    override fun toString(): String {
        return "$hours:$minutes"
    }

    companion object {
        const val EMPTY = "--:--"
    }
}