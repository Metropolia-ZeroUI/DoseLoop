package com.example.doseloop

import android.os.Bundle
import android.util.Log
import androidx.navigation.navArgs
import com.example.doseloop.databinding.ActivityConfirmPhoneNumberChangeBinding
import com.example.doseloop.viewmodel.PhoneNumberSettingViewModel

/**
 * Creates a confirmation popup window when saving a new number in the PhoneNumberSettingFragment.
 */
class ConfirmPhoneNumberChangeActivity : PopupActivity<PhoneNumberSettingViewModel>() {

    private lateinit var binding: ActivityConfirmPhoneNumberChangeBinding
    private val args : ConfirmPhoneNumberChangeActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = args.phoneNumberSettingViewModel
        binding = ActivityConfirmPhoneNumberChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtons()
        binding.confirmWindowTitle.text = getString(R.string.change_number, args.numberKeySimple, args.number)
    }

    private fun setButtons() {
        binding.popupConfirmButton.setOnClickListener {
            viewModel?.onPopupConfirm(args.msg, args.numberKey, args.number)
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