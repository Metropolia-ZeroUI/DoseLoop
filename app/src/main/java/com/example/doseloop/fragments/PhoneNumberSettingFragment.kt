package com.example.doseloop.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.text.isDigitsOnly
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.doseloop.R
import com.example.doseloop.comms.impl.Message
import com.example.doseloop.comms.impl.PhoneNumber
import com.example.doseloop.comms.impl.SmsMessageService
import com.example.doseloop.databinding.FragmentPhoneNumberSettingBinding
import com.example.doseloop.util.*
import com.example.doseloop.viewmodel.AbstractViewModel
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

        val view = binding.root

        val screenWidth = requireContext().resources.displayMetrics.widthPixels

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

        binding.phoneNumberBackButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_phoneNumberSettingFragment_to_homeFragment)
        }

        return view
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

    private fun addSubmitButtonListener(submitButton: Button, editText: EditText, phoneSet: Message, numberKey: String, numberKeySimple: String) {
        submitButton.setOnClickListener {
            val number = editText.text.toString()
            preventButtonClickSpam {
                if (viewModel != null) {
                    val action =
                        PhoneNumberSettingFragmentDirections
                            .actionPhoneNumberSettingFragmentToConfirmWindowActivity(number, numberKey, phoneSet.withPayload(PhoneNumber(number)), numberKeySimple)
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