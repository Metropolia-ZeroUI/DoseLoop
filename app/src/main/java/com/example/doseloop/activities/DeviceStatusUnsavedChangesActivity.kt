package com.example.doseloop.activities

import android.os.Bundle
import androidx.navigation.navArgs
import com.example.doseloop.PopupActivity
import com.example.doseloop.R
import com.example.doseloop.databinding.ActivityDeviceStatusUnsavedChangesBinding
import com.example.doseloop.util.NAVIGATE_TO_HOME_FRAGMENT
import com.example.doseloop.viewmodel.DeviceStatusViewModel

class DeviceStatusUnsavedChangesActivity : PopupActivity<DeviceStatusViewModel>(
    DeviceStatusViewModel()
) {
    private lateinit var binding: ActivityDeviceStatusUnsavedChangesBinding
    private val args : DeviceStatusUnsavedChangesActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceStatusUnsavedChangesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtons()
        binding.confirmWindowTitle.text = getString(R.string.unsaved_changes, args.changes)
    }

    private fun setButtons() {
        binding.popupConfirmButton.setOnClickListener {
            viewModel?.saveToPrefs(NAVIGATE_TO_HOME_FRAGMENT, true)
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