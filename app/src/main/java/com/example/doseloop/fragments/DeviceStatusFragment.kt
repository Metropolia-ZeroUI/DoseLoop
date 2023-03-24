package com.example.doseloop.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.doseloop.R
import com.example.doseloop.comms.impl.Message
import com.example.doseloop.comms.impl.PhoneNumber
import com.example.doseloop.comms.impl.SmsMessageService
import com.example.doseloop.databinding.FragmentDeviceStatusBinding
import com.example.doseloop.util.DEVICE_PHONE_NUMBER
import com.example.doseloop.viewmodel.DeviceStatusViewModel
import com.example.doseloop.viewmodel.PhoneNumberSettingViewModel

/**
 * Fragment for sending query SMS' to the device
 */

class DeviceStatusFragment : AbstractFragment<DeviceStatusViewModel>(DeviceStatusViewModel()) {

    private var _binding: FragmentDeviceStatusBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDeviceStatusBinding.inflate(inflater, container, false)

        val view = binding.root

        setOnButtonPress(binding.statusButtonPhoneNumbers, Message.ADMIN_PHONE_NUMBER_QUERY)
        setOnButtonPress(binding.statusButtonSmsNumbers, Message.ADMIN_SMS_QUERY)
        setOnButtonPress(binding.statusButtonSetTime, Message.ADMIN_SET_TIME)
        setOnButtonPress(binding.statusButtonMedicineTimes, Message.ADMIN_LIST_MEDS)
        setOnButtonPress(binding.statusButtonSignalStrength, Message.ADMIN_SIGNAL_STRENGTH)
        setOnButtonPress(binding.statusButtonBatteryLevel, Message.ADMIN_BATTERY_LIFE)
        setOnButtonPress(binding.statusButtonDoorTimes, Message.ADMIN_DOOR_OPENING_TIMES)

        binding.statusBackButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_deviceStatusFragment_to_homeFragment)
        }

        return view
    }

    private fun setOnButtonPress(button: Button, msg: Message) {
        val deviceNumber = PhoneNumberSettingViewModel().getFromPrefs(DEVICE_PHONE_NUMBER, "")
        val msgService = SmsMessageService(PhoneNumber(deviceNumber!!), requireContext())
        button.setOnClickListener {
            msgService.sendMessage(msg) {
                Log.d("MESSAGE_SEND", "Message OK")
            }
        }
    }
}