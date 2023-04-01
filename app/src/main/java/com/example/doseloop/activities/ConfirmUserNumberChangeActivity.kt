package com.example.doseloop.activities

import android.os.Bundle
import androidx.navigation.navArgs
import com.example.doseloop.PopupActivity
import com.example.doseloop.R
import com.example.doseloop.databinding.ActivityConfirmUserNumberChangeBinding
import com.example.doseloop.viewmodel.DeviceStatusViewModel

class ConfirmUserNumberChangeActivity : PopupActivity<DeviceStatusViewModel>(DeviceStatusViewModel()) {
    private lateinit var binding: ActivityConfirmUserNumberChangeBinding
    private val args : ConfirmUserNumberChangeActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmUserNumberChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtons()
        binding.confirmWindowTitle.text = getString(R.string.set_user_number, args.number)
    }

    private fun setButtons() {
        binding.popupConfirmButton.setOnClickListener {
            viewModel?.onPopupConfirmUserNumber(args.number)
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