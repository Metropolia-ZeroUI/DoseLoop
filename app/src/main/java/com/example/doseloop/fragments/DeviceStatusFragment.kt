package com.example.doseloop.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.doseloop.R
import com.example.doseloop.comms.impl.Message
import com.example.doseloop.databinding.FragmentDeviceStatusBinding
import com.example.doseloop.viewmodel.DeviceStatusViewModel

/**
 * Fragment for sending query SMS' to the device
 */

class DeviceStatusFragment : AbstractFragment<DeviceStatusViewModel>(DeviceStatusViewModel()) {

    private var _binding: FragmentDeviceStatusBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDeviceStatusBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        setOnButtonPress(binding.statusButtonPhoneNumbers, Message.ADMIN_PHONE_NUMBER_QUERY, getString(R.string.confirm_status_phone_numbers))
        setOnButtonPress(binding.statusButtonSmsNumbers, Message.ADMIN_SMS_QUERY, getString(R.string.confirm_status_numbers_settings))
        setOnButtonPress(binding.statusButtonSetTime, Message.ADMIN_SET_TIME, getString(R.string.confirm_status_time_correct))
        setOnButtonPress(binding.statusButtonMedicineTimes, Message.ADMIN_LIST_MEDS, getString(R.string.confirm_status_medicine_times))
        setOnButtonPress(binding.statusButtonSignalStrength, Message.ADMIN_SIGNAL_STRENGTH, getString(R.string.confirm_status_signal_strength))
        setOnButtonPress(binding.statusButtonBatteryLevel, Message.ADMIN_BATTERY_LIFE, getString(R.string.confirm_status_battery))
        setOnButtonPress(binding.statusButtonDoorTimes, Message.ADMIN_DOOR_OPENING_TIMES, getString(R.string.confirm_status_door_times))
        setOnButtonPress(binding.alarmRemove, Message.USER_PHONE_DISABLE, getString(R.string.alarm_off_confirm))
        setOnButtonPress(binding.alarmCall, Message.USER_PHONE_MEDS_REMINDER_CALL, getString(R.string.alarm_call_confirm))
        setOnButtonPress(binding.alarmSms, Message.USER_PHONE_MEDS_REMINDER_SMS, getString(R.string.alarm_sms_confirm))

        setToolBarBackButton(binding.deviceToolbar)

        binding.deviceLockSwitch.isChecked = viewModel?.getLockedState()!!

        binding.saveDeviceChangesButton.setOnClickListener {
            preventButtonClickSpam {
                val action =
                    DeviceStatusFragmentDirections
                        .actionDeviceStatusFragmentToConfirmDeviceLockChangesActivity2(binding.deviceLockSwitch.isChecked)
                findNavController().navigate(action)
            }
        }

        return binding.root
    }

    private fun setOnButtonPress(button: Button, msg: Message, confirmText: String) {
        button.setOnClickListener {
            preventButtonClickSpam {
                if (viewModel != null) {
                    val action =
                        DeviceStatusFragmentDirections
                            .actionDeviceStatusFragmentToConfirmStatusActivity(msg, confirmText)
                    findNavController().navigate(action)
                }
            }
        }
    }
}