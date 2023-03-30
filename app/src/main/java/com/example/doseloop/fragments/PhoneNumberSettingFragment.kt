package com.example.doseloop.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.navigation.fragment.findNavController
import com.example.doseloop.R
import com.example.doseloop.comms.impl.Message
import com.example.doseloop.comms.impl.PhoneNumber
import com.example.doseloop.databinding.FragmentPhoneNumberSettingBinding
import com.example.doseloop.speech.SpeechListener
import com.example.doseloop.speech.SpeechToText
import com.example.doseloop.util.*
import com.example.doseloop.viewmodel.PhoneNumberSettingViewModel
import com.google.android.material.textfield.TextInputLayout


/**
 * Fragment for setting the phone numbers the dispenser uses for alerts
 */

class PhoneNumberSettingFragment : AbstractFragment<PhoneNumberSettingViewModel>(
    PhoneNumberSettingViewModel()
) {

    private var _binding: FragmentPhoneNumberSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPhoneNumberSettingBinding.inflate(inflater, container, false)
        binding.viewModel = this.viewModel
        val view = binding.root

        val screenWidth = requireContext().resources.displayMetrics.widthPixels

        // Setup speech to text
        val speechToTxt = SpeechToText(requireContext(), listener = SpeechListener(
            onSuccess = { Log.i(SpeechListener::class.simpleName, "Speech got recognized") },
            onError = { Log.e(SpeechListener::class.simpleName, "Failed to recognize speech") },
            onReady = { Log.i(SpeechListener::class.simpleName, "Ready") },
            onEnd = { Log.d(SpeechListener::class.simpleName, "End")}
        ))

        // Programmatically lengthening the TextInputEditText's as their length can't properly be set in the layout xml
        binding.number1EditText.width = screenWidth / 2
        binding.number2EditText.width = screenWidth / 2
        binding.number3EditText.width = screenWidth / 2
        binding.number4EditText.width = screenWidth / 2
        binding.number5EditText.width = screenWidth / 2

        // Add EditText error handling
        addTextChangedListener(binding.number1EditText, binding.number1SubmitButton, binding.number1TextInputLayout)
        addTextChangedListener(binding.number2EditText, binding.number2SubmitButton, binding.number2TextInputLayout)
        addTextChangedListener(binding.number3EditText, binding.number3SubmitButton, binding.number3TextInputLayout)
        addTextChangedListener(binding.number4EditText, binding.number4SubmitButton, binding.number4TextInputLayout)
        addTextChangedListener(binding.number5EditText, binding.number5SubmitButton, binding.number5TextInputLayout)

        // Pre-set current numbers to fields
        binding.number1EditText.setText(viewModel?.getFromPrefs(PHONE_NUMBER_1, ""))
        binding.number2EditText.setText(viewModel?.getFromPrefs(PHONE_NUMBER_2, ""))
        binding.number3EditText.setText(viewModel?.getFromPrefs(PHONE_NUMBER_3, ""))
        binding.number4EditText.setText(viewModel?.getFromPrefs(PHONE_NUMBER_4, ""))
        binding.number5EditText.setText(viewModel?.getFromPrefs(PHONE_NUMBER_5, ""))

        // Set submit button listeners for each field
        addSubmitButtonListener(binding.number1SubmitButton, binding.number1EditText, Message.PHONE_SET_1, PHONE_NUMBER_1, "1")
        addSubmitButtonListener(binding.number2SubmitButton, binding.number2EditText, Message.PHONE_SET_2, PHONE_NUMBER_2, "2")
        addSubmitButtonListener(binding.number3SubmitButton, binding.number3EditText, Message.PHONE_SET_3, PHONE_NUMBER_3, "3")
        addSubmitButtonListener(binding.number4SubmitButton, binding.number4EditText, Message.PHONE_SET_4, PHONE_NUMBER_4, "4")
        addSubmitButtonListener(binding.number5SubmitButton, binding.number5EditText, Message.PHONE_SET_5, PHONE_NUMBER_5, "5")


        binding.submitNotifyChanges.setOnClickListener {
            if (this.viewModel != null) {
                val action =
                    PhoneNumberSettingFragmentDirections
                        .actionPhoneNumberSettingFragmentToConfirmNotificationChangeActivity(
                            viewModel.number1Notification.value!!,
                            viewModel.number2Notification.value!!,
                            viewModel.number3Notification.value!!,
                            viewModel.number4Notification.value!!,
                            viewModel.number5Notification.value!!
                        )
                this.findNavController().navigate(action)
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val unsavedChanges = viewModel?.getUnsavedChanges(
                    binding.number1EditText.text.toString(),
                    binding.number2EditText.text.toString(),
                    binding.number3EditText.text.toString(),
                    binding.number4EditText.text.toString(),
                    binding.number5EditText.text.toString(),
                    )
                if (unsavedChanges!!.isNotEmpty()) {
                    val action =
                        PhoneNumberSettingFragmentDirections
                            .actionPhoneNumberSettingFragmentToPhoneNumberSettingUnsavedChangesActivity(unsavedChanges)
                    findNavController().navigate(action)

                } else {
                    findNavController().popBackStack(R.id.homeFragment, false)
                }
            }
        })

        setToolBarBackButton(binding.settingsTolbar)

        // Set record button listeners for each field
        binding.number1TextInputLayout.tag = "1"
        addRecordVoiceButtonListener(binding.number1TextInputLayout, binding.number1EditText, speechToTxt, "1")
        binding.number2TextInputLayout.tag = "1"
        addRecordVoiceButtonListener(binding.number2TextInputLayout, binding.number2EditText, speechToTxt, "2")
        binding.number3TextInputLayout.tag = "1"
        addRecordVoiceButtonListener(binding.number3TextInputLayout, binding.number3EditText, speechToTxt, "3")
        binding.number4TextInputLayout.tag = "1"
        addRecordVoiceButtonListener(binding.number4TextInputLayout, binding.number4EditText, speechToTxt, "4")
        binding.number5TextInputLayout.tag = "1"
        addRecordVoiceButtonListener(binding.number5TextInputLayout, binding.number5EditText, speechToTxt, "5")
        return view
    }

    override fun onResume() {
        super.onResume()
        if (viewModel?.getFromPrefs(NAVIGATE_TO_HOME_FRAGMENT, false) == true) {
            findNavController().popBackStack(R.id.homeFragment, false)
            viewModel?.saveToPrefs(NAVIGATE_TO_HOME_FRAGMENT, false)
        }
    }

    private fun addTextChangedListener(editText: EditText, submitButton: Button, til: TextInputLayout) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val charSequence = p0 ?: ""
                handleTextErrors(submitButton, charSequence, til)
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }
    private fun addRecordVoiceButtonListener(til: TextInputLayout, editText: EditText, speechToText: SpeechToText, position: String) {
        til.setEndIconOnClickListener {
            if(til.tag == "2") {
                speechToText.stopListening()
                til.setEndIconDrawable(R.drawable.ic_mic)
                til.setEndIconTintList(ColorStateList.valueOf(resources.getColor(R.color.dark_gray)))
                editText.hint = ""
                editText.clearFocus()
                editText.setText(viewModel?.getFromPrefs("PHONE_NUMBER_${position}", ""))
                til.tag = "1"
            }
            else {
                var userText = ""
                til.tag = "2"
                til.setEndIconDrawable(R.drawable.ic_mic_record)
                til.setEndIconTintList(ColorStateList.valueOf(resources.getColor(R.color.wine_red)))
                editText.setText("")
                editText.hint = "Listening..."
                editText.requestFocus()
                speechToText.tryRecognize(this) {
                    userText = it
                    til.setEndIconDrawable(R.drawable.ic_mic)
                    til.setEndIconTintList(ColorStateList.valueOf(resources.getColor(R.color.dark_gray)))
                    if (userText != "") {
                        userText = userText.replace(" ", "")
                        if(userText.trim()[0] == '0') {
                            editText.setText(userText)
                            editText.hint = ""
                            editText.clearFocus()
                        } else {
                            val toast = Toast.makeText(activity, "'$userText' ei ole sopiva puhelinnumero", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                            editText.hint = ""
                            editText.clearFocus()
                            editText.setText(viewModel?.getFromPrefs("PHONE_NUMBER_${position}", ""))
                        }
                        til.setEndIconDrawable(R.drawable.ic_mic)
                        til.setEndIconTintList(ColorStateList.valueOf(resources.getColor(R.color.dark_gray)))
                        til.tag = "1"
                    }
                }

            }
        }
    }

    private fun addSubmitButtonListener(submitButton: Button, editText: EditText, phoneSet: Message, numberKey: String, numberKeySimple: String) {
        submitButton.setOnClickListener {
            val number = editText.text.toString()
            preventButtonClickSpam {
                if (viewModel != null) {
                    editText.clearFocus()
                    val action =
                        PhoneNumberSettingFragmentDirections
                            .actionPhoneNumberSettingFragmentToConfirmWindowActivity(number, numberKey, phoneSet.withPayload(PhoneNumber(number)), numberKeySimple, viewModel)
                    findNavController().navigate(action)
                }
            }
        }
    }

    /**
     * Make sure input is only max 10 characters, there's no country code added, that the first number is a zero and that there are no other special characters.
     * The + check is done separately as we want a separate error message for possible country codes.
     */
    private fun handleTextErrors(button: Button, text: CharSequence, til: TextInputLayout) {
        var error: Boolean
        val isDigit = text.toString().trim().isDigitsOnly()

        if (text.toString().trim().length > 10) {
            til.error = getString(R.string.error_too_long_number)
            error = true
        } else if (text.toString().trim().contains('+')) {
            til.error = getString(R.string.error_country_code)
            error = true
        } else if (text.toString().trim().isNotEmpty() && text.toString().trim()[0] != '0') {
            til.error = getString(R.string.error_invalid_first_number)
            error = true
        } else if (!isDigit) {
            til.error = getString(R.string.error_not_digit)
            error = true
        } else {
            til.error = null
            error = false
        }

        // Disable button but no error if field is empty
        if (text.toString().trim().isEmpty()) {
            error = true
        }

        button.isEnabled = !error
    }
}