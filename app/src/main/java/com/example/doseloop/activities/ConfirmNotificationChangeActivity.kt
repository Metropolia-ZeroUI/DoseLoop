package com.example.doseloop.activities

import android.os.Bundle
import androidx.navigation.navArgs
import com.example.doseloop.PopupActivity
import com.example.doseloop.R
import com.example.doseloop.databinding.ActivityConfirmPhoneNumberChangeBinding
import com.example.doseloop.viewmodel.PhoneNumberSettingViewModel

class ConfirmNotificationChangeActivity : PopupActivity<PhoneNumberSettingViewModel>() {

    private lateinit var binding: ActivityConfirmPhoneNumberChangeBinding
    private val args : ConfirmNotificationChangeActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        height = 1000
        super.onCreate(savedInstanceState)
        viewModel = args.phoneNumberSettingViewModel
        binding = ActivityConfirmPhoneNumberChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtons()
        binding.confirmWindowTitle.text = getString(R.string.notify_changes, viewModel?.getNotifiedNumbersToString(), viewModel?.getNotNotifiedNumbersToString())
    }

    private fun setButtons() {
        binding.popupConfirmButton.setOnClickListener {
            viewModel?.updateNotificationReceivers()
            finish()
        }
        binding.popupCancelButton.setOnClickListener {
            finish()
        }
        binding.confirmCloseButton.setOnClickListener {
            finish()
        }
    }
}