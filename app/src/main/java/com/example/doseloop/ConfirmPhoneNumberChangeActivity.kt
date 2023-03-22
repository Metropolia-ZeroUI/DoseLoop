package com.example.doseloop

import android.os.Bundle
import androidx.navigation.navArgs
import com.example.doseloop.databinding.ActivityConfirmPhoneNumberChangeBinding
import com.example.doseloop.viewmodel.PhoneNumberSettingViewModel

/**
 * Creates a confirmation popup window when saving a new number in the PhoneNumberSettingFragment.
 */
class ConfirmPhoneNumberChangeActivity : PopupActivity<PhoneNumberSettingViewModel>(PhoneNumberSettingViewModel()) {

    private lateinit var binding: ActivityConfirmPhoneNumberChangeBinding
    private val args : ConfirmPhoneNumberChangeActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmPhoneNumberChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtons()
        binding.confirmWindowTitle.text = getString(R.string.aseta_numero, args.numberKeySimple)
        binding.confirmWindowNumber.text = getString(R.string.uusi_numero, args.number)
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