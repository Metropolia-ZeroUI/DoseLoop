package com.example.doseloop.activities

import android.os.Bundle
import androidx.navigation.navArgs
import com.example.doseloop.PopupActivity
import com.example.doseloop.R
import com.example.doseloop.databinding.ActivityConfirmDeviceLockChangesBinding
import com.example.doseloop.viewmodel.DeviceStatusViewModel

class ConfirmDeviceChangesActivity : PopupActivity<DeviceStatusViewModel>(
    DeviceStatusViewModel()
) {

    private lateinit var binding: ActivityConfirmDeviceLockChangesBinding
    private val args : ConfirmDeviceChangesActivityArgs by navArgs()
    private var lockedState = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmDeviceLockChangesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lockedState = args.lockedState
        setButtons()
        binding.confirmWindowTitle.text = getString(R.string.device_status_save_info, viewModel?.getChanges())
    }

    private fun setButtons() {
        binding.popupConfirmButton.setOnClickListener {
            viewModel?.saveDeviceLockedState(lockedState)
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