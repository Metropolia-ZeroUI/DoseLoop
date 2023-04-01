package com.example.doseloop.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.doseloop.R
import com.example.doseloop.comms.impl.Message
import com.example.doseloop.comms.impl.PhoneNumber
import com.example.doseloop.databinding.FragmentDeviceStatusBinding
import com.example.doseloop.speech.SpeechListener
import com.example.doseloop.speech.SpeechToText
import com.example.doseloop.util.DEVICE_USER_NUMBER
import com.example.doseloop.util.NAVIGATE_TO_HOME_FRAGMENT
import com.example.doseloop.util.PHONE_NUMBER_1
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

        val speechToTxt = SpeechToText(requireContext(), listener = SpeechListener(
            onSuccess = { Log.i(SpeechListener::class.simpleName, "Speech got recognized") },
            onError = { Log.e(SpeechListener::class.simpleName, "Failed to recognize speech") },
            onReady = { Log.i(SpeechListener::class.simpleName, "Ready") },
            onEnd = { Log.d(SpeechListener::class.simpleName, "End")}
        ))

        addTextChangedListener(binding.deviceOwnerEdittext, binding.ownerNumberSubmitButton, binding.deviceOwnerInputlayout)
        addSubmitButtonListener(binding.ownerNumberSubmitButton, binding.deviceOwnerEdittext)
        binding.deviceOwnerEdittext.setText(viewModel?.getFromPrefs(DEVICE_USER_NUMBER, ""))
        binding.deviceOwnerInputlayout.tag = "1"
        addRecordVoiceButtonListener(binding.deviceOwnerInputlayout, binding.deviceOwnerEdittext, speechToTxt, "6")

        binding.deviceLockSwitch.isChecked = viewModel?.getLockedState()!!

        binding.saveDeviceChangesButton.setOnClickListener {
            preventButtonClickSpam {
                val action =
                    DeviceStatusFragmentDirections
                        .actionDeviceStatusFragmentToConfirmDeviceLockChangesActivity2(binding.deviceLockSwitch.isChecked)
                findNavController().navigate(action)
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val unsavedChanges = viewModel.getChanges(binding.deviceOwnerEdittext.text.toString(), binding.deviceLockSwitch.isChecked)
                if (unsavedChanges.isNotEmpty()) {
                    val action =
                        DeviceStatusFragmentDirections
                            .actionDeviceStatusFragmentToDeviceStatusUnsavedChangesActivity(unsavedChanges)
                    findNavController().navigate(action)

                } else {
                    findNavController().popBackStack(R.id.homeFragment, false)
                }
            }
        })
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (viewModel?.getFromPrefs(NAVIGATE_TO_HOME_FRAGMENT, false) == true) {
            findNavController().popBackStack(R.id.homeFragment, false)
            viewModel.saveToPrefs(NAVIGATE_TO_HOME_FRAGMENT, false)
        }
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

    private fun addSubmitButtonListener(submitButton: Button, editText: EditText) {
        submitButton.setOnClickListener {
            val number = editText.text.toString()
            preventButtonClickSpam {
                if (viewModel != null) {
                    editText.clearFocus()
                    val action =
                        DeviceStatusFragmentDirections
                            .actionDeviceStatusFragmentToConfirmUserNumberChangeActivity(number)
                    findNavController().navigate(action)
                }
            }
        }
    }

}