package com.example.doseloop

import android.os.Bundle
import android.util.Log
import androidx.navigation.navArgs
import com.example.doseloop.databinding.ActivityConfirmMedicineTimesBinding
import com.example.doseloop.viewmodel.DateTimeSettingViewModel

class ConfirmMedicineTimesActivity : PopupActivity<DateTimeSettingViewModel>(DateTimeSettingViewModel()) {
    private lateinit var binding: ActivityConfirmMedicineTimesBinding
    private val args : ConfirmMedicineTimesActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfirmMedicineTimesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtons()
        val period = if (!args.period) getString(R.string.every_day_small) else getString(R.string.every_other_day_small)
        binding.confirmWindowTitle.text = if (!args.delete) getString(R.string.change_time, args.timeKeySimple, args.time, period) else getString(R.string.delete_time, args.timeKeySimple)
    }

    private fun setButtons() {
        binding.popupConfirmButton.setOnClickListener {
            Log.d("confirm", "confirming")
            viewModel?.onPopupConfirm(args.msg, args.timeKey, args.time, args.dayKey, args.dateKey, args.period)
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

