package com.example.doseloop.activities

import android.os.Bundle
import com.example.doseloop.PopupActivity
import com.example.doseloop.R
import com.example.doseloop.databinding.ActivityConfirmDeviceLockChangesBinding
import com.example.doseloop.viewmodel.DeviceStatusViewModel
import com.example.doseloop.viewmodel.PhoneNumberSettingViewModel

class ConfirmDeviceLockChangesActivity : PopupActivity<DeviceStatusViewModel>(
    DeviceStatusViewModel()
) {

/*    private lateinit var binding: ActivityConfirmDeviceLockChangesBinding
  //  private val args : ConfirmNotificationChangeActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmDeviceLockChangesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtons()

        // TODO: Get actual lockedState
        val lockedState = true

//        viewModel?.updateNotifyReceivers(args.n1, args.n2, args.n3, args.n4, args.n5)
        binding.confirmWindowTitle.text = getString(if (lockedState) R.string.device_lock_info else R.string.device_open_info)
    }

    private fun setButtons() {
//        binding.popupConfirmButton.setOnClickListener {
//            viewModel?.updateNotificationReceiversToDevice()
//            finish()
//        }
//        binding.popupCancelButton.setOnClickListener {
//            finish()
//        }
//        binding.confirmCloseButton.setOnClickListener {
//            finish()
//        }
    }*/
}