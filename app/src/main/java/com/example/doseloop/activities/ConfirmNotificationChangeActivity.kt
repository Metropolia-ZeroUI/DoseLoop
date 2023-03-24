package com.example.doseloop.activities

import android.os.Bundle
import android.util.Log
import androidx.navigation.navArgs
import com.example.doseloop.PopupActivity
import com.example.doseloop.R
import com.example.doseloop.databinding.ActivityConfirmPhoneNumberChangeBinding
import com.example.doseloop.viewmodel.PhoneNumberSettingViewModel

class ConfirmNotificationChangeActivity : PopupActivity<PhoneNumberSettingViewModel>(
    PhoneNumberSettingViewModel()
) {

    private lateinit var binding: ActivityConfirmPhoneNumberChangeBinding
    private val args : ConfirmNotificationChangeActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmPhoneNumberChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtons()
        viewModel?.updateNotifyReceivers(args.n1, args.n2, args.n3, args.n4, args.n5)
        binding.confirmWindowTitle.text = getString(R.string.notify_changes, viewModel?.getNotifiedNumbersToString(), viewModel?.getNotNotifiedNumbersToString())
    }

    private fun setButtons() {
        binding.popupConfirmButton.setOnClickListener {
            viewModel?.updateNotificationReceiversToDevice()
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