package com.example.doseloop.comms.impl

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.doseloop.comms.Destination
import com.example.doseloop.comms.MessageService

class GsmMessageService(private val destination: Destination, private val smsManager: SmsManager): MessageService {

    override fun sendMessage(msg: Message, onSuccess: (() -> Unit)?) {
            Log.d(this::class.simpleName, "sendMessage: [$msg] -- ${msg.encode()}")
            smsManager.sendTextMessage(destination.getAddress(), null, msg.encode(),
                null, null)
            onSuccess?.invoke()
    }

    companion object {
        fun checkPermissions(activity: Activity) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.SEND_SMS), 100);
            }
        }
    }
}