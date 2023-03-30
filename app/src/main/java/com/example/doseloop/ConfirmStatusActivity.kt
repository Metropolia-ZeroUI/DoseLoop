package com.example.doseloop

import android.os.Bundle
import androidx.navigation.navArgs
import com.example.doseloop.comms.impl.Message
import com.example.doseloop.databinding.ActivityConfirmStatusBinding
import com.example.doseloop.viewmodel.DeviceStatusViewModel

class ConfirmStatusActivity : PopupActivity<DeviceStatusViewModel>(DeviceStatusViewModel()){
    private lateinit var binding: ActivityConfirmStatusBinding
    private val args : ConfirmStatusActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtons()
        binding.confirmWindowTitle.text = args.confirmText
    }

    private fun setButtons() {
        binding.popupConfirmButton.setOnClickListener {
            viewModel?.onPopupConfirm(args.msg)
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