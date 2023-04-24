package com.example.doseloop.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.doseloop.util.*
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.fragment.findNavController
import com.example.doseloop.R
import com.example.doseloop.comms.impl.Message
import com.example.doseloop.comms.util.TimeOfDay24
import com.example.doseloop.databinding.FragmentDateTimeSettingBinding
import com.example.doseloop.speech.SpeechListener
import com.example.doseloop.speech.SpeechToText
import com.example.doseloop.viewmodel.DateTimeSettingViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.LocalDate


/**
 * Fragment for setting the date and time of taking medicine
 */
class DateTimeSettingFragment : AbstractFragment<DateTimeSettingViewModel>(
    DateTimeSettingViewModel()
) {

    private var _binding: FragmentDateTimeSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDateTimeSettingBinding.inflate(inflater, container, false)

        val view = binding.root

        // Setup speech to text
        val speechToTxt = SpeechToText(requireContext(), listener = SpeechListener(
            onSuccess = { Log.i(SpeechListener::class.simpleName, "Speech got recognized") },
            onError = { Log.e(SpeechListener::class.simpleName, "Failed to recognize speech") },
            onReady = { Log.i(SpeechListener::class.simpleName, "Ready") },
            onEnd = { Log.d(SpeechListener::class.simpleName, "End")}
        ))

        addChangedListener(binding.time1EditText,  binding.time1SubmitButton, binding.daySlider, binding.time1EditText, 1)
        addChangedListener(binding.time2EditText, binding.time2SubmitButton, binding.day2Slider, binding.time2EditText, 2)
        addChangedListener(binding.time3EditText, binding.time3SubmitButton, binding.day3Slider, binding.time3EditText, 3)
        addChangedListener(binding.time4EditText, binding.time4SubmitButton, binding.day4Slider, binding.time4EditText, 4)
        addChangedListener(binding.time5EditText, binding.time5SubmitButton, binding.day5Slider, binding.time5EditText, 5)
        addChangedListener(binding.time6EditText, binding.time6SubmitButton, binding.day6Slider, binding.time6EditText, 6)

        addTimePicker(binding.time1EditText)
        addTimePicker(binding.time2EditText)
        addTimePicker(binding.time3EditText)
        addTimePicker(binding.time4EditText)
        addTimePicker(binding.time5EditText)
        addTimePicker(binding.time6EditText)

        // Pre-set current times fields
        binding.time1EditText.text = DateTimeSettingViewModel().getFromPrefs(DATE_TIME_1, "")
        binding.time2EditText.text = DateTimeSettingViewModel().getFromPrefs(DATE_TIME_2, "")
        binding.time3EditText.text = DateTimeSettingViewModel().getFromPrefs(DATE_TIME_3, "")
        binding.time4EditText.text = DateTimeSettingViewModel().getFromPrefs(DATE_TIME_4, "")
        binding.time5EditText.text = DateTimeSettingViewModel().getFromPrefs(DATE_TIME_5, "")
        binding.time6EditText.text = DateTimeSettingViewModel().getFromPrefs(DATE_TIME_6, "")

        // Pre-set current days fields
        binding.daySlider.isChecked = DateTimeSettingViewModel().getFromPrefs(DAY_1, false)
        binding.day2Slider.isChecked = DateTimeSettingViewModel().getFromPrefs(DAY_2, false)
        binding.day3Slider.isChecked = DateTimeSettingViewModel().getFromPrefs(DAY_3, false)
        binding.day4Slider.isChecked = DateTimeSettingViewModel().getFromPrefs(DAY_4, false)
        binding.day5Slider.isChecked = DateTimeSettingViewModel().getFromPrefs(DAY_5, false)
        binding.day6Slider.isChecked = DateTimeSettingViewModel().getFromPrefs(DAY_6, false)

        // Set submit button listeners for each field
        addSubmitButtonListener(binding.time1SubmitButton, binding.time1EditText, binding.daySlider, Message.TIME_FOR_MEDS_1, DATE_TIME_1, DAY_1, DATE_KEY_1, "1")
        addSubmitButtonListener(binding.time2SubmitButton, binding.time2EditText, binding.day2Slider, Message.TIME_FOR_MEDS_2, DATE_TIME_2, DAY_2, DATE_KEY_2, "2")
        addSubmitButtonListener(binding.time3SubmitButton, binding.time3EditText, binding.day3Slider, Message.TIME_FOR_MEDS_3, DATE_TIME_3, DAY_3, DATE_KEY_3, "3")
        addSubmitButtonListener(binding.time4SubmitButton, binding.time4EditText, binding.day4Slider, Message.TIME_FOR_MEDS_4, DATE_TIME_4, DAY_4, DATE_KEY_4, "4")
        addSubmitButtonListener(binding.time5SubmitButton, binding.time5EditText, binding.day5Slider, Message.TIME_FOR_MEDS_5, DATE_TIME_5, DAY_5, DATE_KEY_5, "5")
        addSubmitButtonListener(binding.time6SubmitButton, binding.time6EditText, binding.day6Slider, Message.TIME_FOR_MEDS_6, DATE_TIME_6, DAY_6, DATE_KEY_6, "6")

        // Set delete button listeners for each field
        addDeleteButtonListener(binding.time1DeleteButton, binding.time1EditText, binding.daySlider, Message.TIME_FOR_MEDS_1, DATE_TIME_1, DAY_1, DATE_KEY_1, "1")
        addDeleteButtonListener(binding.time2DeleteButton, binding.time2EditText, binding.day2Slider, Message.TIME_FOR_MEDS_2, DATE_TIME_2, DAY_2, DATE_KEY_2, "2")
        addDeleteButtonListener(binding.time3DeleteButton, binding.time3EditText, binding.day3Slider, Message.TIME_FOR_MEDS_3, DATE_TIME_3, DAY_3, DATE_KEY_3, "3")
        addDeleteButtonListener(binding.time4DeleteButton, binding.time4EditText, binding.day4Slider, Message.TIME_FOR_MEDS_4, DATE_TIME_4, DAY_4, DATE_KEY_4, "4")
        addDeleteButtonListener(binding.time5DeleteButton, binding.time5EditText, binding.day5Slider, Message.TIME_FOR_MEDS_5, DATE_TIME_5, DAY_5, DATE_KEY_5, "5")
        addDeleteButtonListener(binding.time6DeleteButton, binding.time6EditText, binding.day6Slider, Message.TIME_FOR_MEDS_6, DATE_TIME_6, DAY_6, DATE_KEY_6, "6")

        addRecordVoiceButtonTimeListener(binding.time1RecordButton ,binding.time1EditText, speechToTxt, "1")

        binding.backHomeButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_dateTimeSettingFragment_to_homeFragment)
        }
        return view
    }

    private fun addTimePicker(textView: TextView) {
        textView.setOnClickListener {
                showTimePicker(textView)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showTimePicker(textView: TextView) {

        val picker = MaterialTimePicker.Builder()
            .setHour(0)
            .setMinute(0)
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setTheme(R.style.CustomTimePickerTheme)
            .build()
        picker.show(parentFragmentManager, null)

        picker.addOnPositiveButtonClickListener{
            val hour = picker.hour
            val minute = picker.minute
            // the format "%02d" specifies that the argument should be formatted as a decimal integer
            val formattedMinute = String.format("%02d", minute)

            Log.d("Show time", "$hour:$formattedMinute")
            textView.text = "$hour:$formattedMinute"

        }
    }

    private fun addChangedListener(textView: TextView, submitButton: Button, switch: SwitchCompat, mess: TextView, day: Int) {

        textView.addTextChangedListener(object : TextWatcher {

            // This method is called after the text is changed
            override fun afterTextChanged(s: Editable?) {
            }
            // This method is called before the text is changed. It takes four parameters:
            // the text itself, the start position of the text which is going to change,
            // the length of the text which is going to change, and the count of the text which will be replaced by new text.
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            // This method is called when the text is changed. It also takes four parameters:
            // the changed text, the start position of the changed text,
            // the length of the changed text, and the count of the text which is replaced by new text.
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                val charSequence = s ?: ""
                handleTextErrors(submitButton, charSequence, mess)
            }
        })
    }

    private fun addSubmitButtonListener(
        submitButton: Button, textView: TextView, switch: SwitchCompat,
        phoneSet: Message, timeKey: String, dayKey: String, dateKey: String, timeKeySimple: String) {

        submitButton.setOnClickListener {
            val time = textView.text.toString()
            val isSwitchChecked = switch.isChecked // if switch is checked then true else false
            val day =  if (isSwitchChecked) Message.MEDS_EVERY_OTHER_DAY else Message.MEDS_EVERY_DAY
            val msg = phoneSet.withPayload("$time,$day")

            preventButtonClickSpam {
                if (viewModel != null) {
                    val action =
                        DateTimeSettingFragmentDirections
                            .actionDateTimeSettingFragmentToConfirmMedicineTimesActivity(
                                /* time = */ time,
                                /* period = */ isSwitchChecked,
                                /* timeKey = */ timeKey,
                                /* timeKeySimple = */ timeKeySimple,
                                /* dayKey = */ dayKey,
                                /* dateKey = */ dateKey,
                                /* msg = */ msg,
                                /* delete = */ false
                            )
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun addDeleteButtonListener(deleteButton: Button, textView: TextView, switch: SwitchCompat,
                                        phoneSet: Message, timeKey: String, dayKey: String, dateKey: String, timeKeySimple: String) {
        deleteButton.setOnClickListener {
            textView.text=""
            switch.isChecked = false
            val msg = phoneSet.withPayload(TimeOfDay24.EMPTY)
            preventButtonClickSpam {
                if (viewModel != null) {
                    val action =
                        DateTimeSettingFragmentDirections
                            .actionDateTimeSettingFragmentToConfirmMedicineTimesActivity(
                                /* time = */ "",
                                /* period = */ false,
                                /* timeKey = */ timeKey,
                                /* timeKeySimple = */ timeKeySimple,
                                /* dayKey = */ dayKey,
                                /* dateKey = */ dateKey,
                                /* msg = */ msg,
                                /* delete = */ true
                            )
                    findNavController().navigate(action)
                }
            }
        }
    }
    /**
     * Check for input
     *
     */
    private fun handleTextErrors(button: Button, text: CharSequence, mess: TextView) {
        var error: Boolean

        if (text.toString().trim().length > 5) {
            mess.error = getString(R.string.set_right_time)
            error = true
        } else {
            mess.error = null
            error = false
        }

        // Disable button but no error if field is empty
        if (text.toString().trim().isEmpty()) {
            error = true
        }
        button.isEnabled = !error
    }

}


