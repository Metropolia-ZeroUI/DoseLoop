package com.example.doseloop.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.doseloop.R
import com.example.doseloop.comms.impl.Message
import com.example.doseloop.databinding.FragmentDeviceStatusBinding
import com.example.doseloop.speech.SpeechListener
import com.example.doseloop.speech.SpeechToText
import com.example.doseloop.util.*
import com.example.doseloop.viewmodel.DateTimeSettingViewModel
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

        setOnButtonPress(binding.statusButtonPhoneNumbers, Message.ADMIN_PHONE_NUMBER_QUERY, getString(R.string.confirm_status_phone_numbers),null)
        setOnButtonPress(binding.statusButtonSmsNumbers, Message.ADMIN_SMS_QUERY, getString(R.string.confirm_status_numbers_settings), null)
        setOnButtonPress(binding.statusButtonSetTime, Message.ADMIN_SET_TIME, getString(R.string.confirm_status_time_correct), null)
        setOnButtonPress(binding.statusButtonMedicineTimes, Message.ADMIN_LIST_MEDS, getString(R.string.confirm_status_medicine_times), null)
        setOnButtonPress(binding.statusButtonSignalStrength, Message.ADMIN_SIGNAL_STRENGTH, getString(R.string.confirm_status_signal_strength), null)
        setOnButtonPress(binding.statusButtonBatteryLevel, Message.ADMIN_BATTERY_LIFE, getString(R.string.confirm_status_battery), null)
        setOnButtonPress(binding.statusButtonDoorTimes, Message.ADMIN_DOOR_OPENING_TIMES, getString(R.string.confirm_status_door_times), null)
        setOnButtonPress(binding.alarmCall, Message.USER_PHONE_MEDS_REMINDER_CALL, getString(R.string.alarm_call_confirm), "call_alarm")
        setOnButtonPress(binding.alarmSms, Message.USER_PHONE_MEDS_REMINDER_SMS, getString(R.string.alarm_sms_confirm), "sms_alarm")
        setOnButtonPress(binding.alarmRemove, Message.USER_PHONE_DISABLE, getString(R.string.alarm_off_confirm), "remove_alarm")

        setToolBarBackButton(binding.deviceToolbar)

        val speechToTxt = SpeechToText(requireContext(), listener = SpeechListener(
            onSuccess = { Log.i(SpeechListener::class.simpleName, "Speech got recognized") },
            onError = { Log.e(SpeechListener::class.simpleName, "Failed to recognize speech") },
            onReady = { Log.i(SpeechListener::class.simpleName, "Ready") },
            onEnd = { Log.d(SpeechListener::class.simpleName, "End")}
        ))

        addTextChangedListener(binding.deviceOwnerEdittext, binding.ownerNumberSubmitButton, binding.deviceOwnerInputlayout)
        addStringTextChangedListener(binding.mealNotEatenEdittext, binding.mealNotEatenSubmitButton, binding.mealNotEatenTextLayout)
        addStringTextChangedListener(binding.fireAlarmEdittext, binding.fireAlarmSubmitButton, binding.fireAlarmTextLayout)
        addStringTextChangedListener(binding.deviceAlarmButtonEdittext, binding.deviceAlarmButtonSubmitButton, binding.deviceAlarmButtonTextLayout)
        addStringTextChangedListener(binding.medicineAlarmEdittext, binding.medicineAlarmSubmitButton, binding.medicineAlarmTextLayout)

        addSubmitButtonListener(binding.ownerNumberSubmitButton, binding.deviceOwnerEdittext, DEVICE_USER_NUMBER, Message.USER_PHONE_ADD, R.string.set_user_number)
        addSubmitButtonListener(binding.medicineAlarmSubmitButton, binding.medicineAlarmEdittext, MEDICINE_ALERT_TEXT, Message.ALARM_MISSED_MEDS, R.string.set_meds_alert_text)
        addSubmitButtonListener(binding.deviceAlarmButtonSubmitButton, binding.deviceAlarmButtonEdittext, ALARM_BUTTON_ALERT_TEXT, Message.ALARM_BUTTON_PRESS, R.string.set_alarm_button_alert_text)
        addSubmitButtonListener(binding.fireAlarmSubmitButton, binding.fireAlarmEdittext, WATER_FIRE_ALERT_TEXT, Message.ALARM_FIRE_WATER, R.string.set_fire_water_text)
        addSubmitButtonListener(binding.mealNotEatenSubmitButton, binding.mealNotEatenEdittext, MEAL_ALERT_TEXT, Message.ALARM_MISSED_FOOD, R.string.set_meal_alarm_text)

        binding.deviceOwnerEdittext.setText(viewModel?.getFromPrefs(DEVICE_USER_NUMBER, ""))
        binding.medicineAlarmEdittext.setText(viewModel?.getFromPrefs(MEDICINE_ALERT_TEXT, ""))
        binding.deviceAlarmButtonEdittext.setText(viewModel?.getFromPrefs(ALARM_BUTTON_ALERT_TEXT, ""))
        binding.fireAlarmEdittext.setText(viewModel?.getFromPrefs(WATER_FIRE_ALERT_TEXT, ""))
        binding.mealNotEatenEdittext.setText(viewModel?.getFromPrefs(MEAL_ALERT_TEXT, ""))

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

//    private fun setOnButtonPress(button: Button, msg: Message, confirmText: String) {
//        button.setOnClickListener {
//            preventButtonClickSpam {
//                if (viewModel != null) {
//                    val action =
//                        DeviceStatusFragmentDirections
//                            .actionDeviceStatusFragmentToConfirmStatusActivity(msg, confirmText)
//                    findNavController().navigate(action)
//                }
//            }
//        }
//    }

    private fun setOnButtonPress(button: Button, msg: Message, confirmText: String, tag: String?) {
        button.setOnClickListener {
            preventButtonClickSpam {
                if (viewModel != null) {
                    val action = DeviceStatusFragmentDirections
                        .actionDeviceStatusFragmentToConfirmStatusActivity(msg, confirmText)
                    findNavController().navigate(action)

                    // Save button state in SharedPreferences
                    tag?.let { nonNullTag ->
                        val sharedPreferences = requireContext().getSharedPreferences("my_app_shared_prefs", Context.MODE_PRIVATE)
                        val clickedTags = HashSet(sharedPreferences.getStringSet("clicked_tags", HashSet<String>()))
                        clickedTags.add(nonNullTag)
                        sharedPreferences.edit().putStringSet("clicked_tags", clickedTags).apply()

                        when (nonNullTag) {
                            "remove_alarm" -> {
                                DeviceStatusViewModel().saveToPrefs(ALARM_STATE_CALL,"")
                                DeviceStatusViewModel().saveToPrefs(ALARM_STATE_SMS,"")
                                DeviceStatusViewModel().saveToPrefs(ALARM_STATE_REMOVE,"remove")
                            }
                            "call_alarm" -> {
                                DeviceStatusViewModel().saveToPrefs(ALARM_STATE_CALL,"call")
                                DeviceStatusViewModel().saveToPrefs(ALARM_STATE_REMOVE,"")
                            }
                            "sms_alarm" -> {
                                DeviceStatusViewModel().saveToPrefs(ALARM_STATE_SMS,"sms")
                                DeviceStatusViewModel().saveToPrefs(ALARM_STATE_REMOVE,"")
                            }
                            else -> {
                                DeviceStatusViewModel().saveToPrefs(ALARM_STATE_REMOVE,"remove")
                            }
                        }
                    }
                }
            }
        }
    }


    private fun addSubmitButtonListener(submitButton: Button, editText: EditText, prefKey: String, msg: Message, desc: Int) {
        submitButton.setOnClickListener {
            val payload = editText.text.toString()
            preventButtonClickSpam {
                if (viewModel != null) {
                    editText.clearFocus()
                    val action =
                        DeviceStatusFragmentDirections
                            .actionDeviceStatusFragmentToConfirmUserNumberChangeActivity(msg.withPayload(payload), payload, prefKey, desc)
                    findNavController().navigate(action)
                }
            }
        }
    }

}