package com.example.doseloop.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.doseloop.R
import com.example.doseloop.databinding.FragmentHomeBinding
import com.example.doseloop.util.*
import com.example.doseloop.viewmodel.DateTimeSettingViewModel
import com.example.doseloop.viewmodel.DeviceStatusViewModel
import com.example.doseloop.viewmodel.HomeFragmentViewModel
import kotlinx.coroutines.launch
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class HomeFragment : AbstractFragment<HomeFragmentViewModel>(HomeFragmentViewModel()) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialization of binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val view = binding.root

        /**
         * A CardView that displays the next upcoming time for taking medication in close proximity.
         *
         */

        // Lock
        val deviceLockedState = DeviceStatusViewModel().getFromPrefs("DEVICE_LOCKED_STATE", false)

        if (deviceLockedState) {
            binding.tvLockStatus.text = getString(R.string.on_lock)
        } else {
            binding.tvLockStatus.text = getString(R.string.unlocked)
            binding.alarmIcon.setImageResource(R.drawable.alarm_off)
        }

        // Call or message
        val alarmStateCall = DeviceStatusViewModel().getFromPrefs("ALARM_STATE_CALL", "")
        val alarmStateSMS = DeviceStatusViewModel().getFromPrefs("ALARM_STATE_SMS", "")
        val alarmStateRemove = DeviceStatusViewModel().getFromPrefs("ALARM_STATE_REMOVE", "")

        when {
            alarmStateRemove?.isNotEmpty() == true -> {
                binding.tvReminderBy.text = getString(R.string.alarm_off)
                binding.reminderIcon.setImageResource(R.drawable.notific_off)
            }
            alarmStateCall?.isNotEmpty() == true && alarmStateSMS?.isNotEmpty() == true -> {
                binding.tvReminderBy.text = getString(R.string.sms_call_alarm)
            }
            alarmStateCall?.isNotEmpty() == true -> {
                binding.tvReminderBy.text = getString(R.string.alarm_call)
            }
            alarmStateSMS?.isNotEmpty() == true -> {
                binding.tvReminderBy.text = getString(R.string.alarm_sms)
            }
            else -> {
                binding.tvReminderBy.text = getString(R.string.alarm_off)
                binding.reminderIcon.setImageResource(R.drawable.notific_off)
            }
        }

        /**
         * Implementation for displaying the next upcoming medicine taking time and day.
         * Commented because has issues in the functionality. Logic should be fixed
         * Start
         */

        // Time & day

//        val inputList = listOf(
//            Triple(DATE_KEY_1, DATE_TIME_1, DAY_1),
//            Triple(DATE_KEY_2, DATE_TIME_2, DAY_2),
//            Triple(DATE_KEY_3, DATE_TIME_3, DAY_3),
//            Triple(DATE_KEY_4, DATE_TIME_4, DAY_4),
//            Triple(DATE_KEY_5, DATE_TIME_5, DAY_5),
//            Triple(DATE_KEY_6, DATE_TIME_6, DAY_6)
//        )
//        viewModel.sortInputDataList(inputList)

        /**
         * End
         */

        // Drawers
        val medicineTimeDrawer = binding.medicineTimeDrawer.binding
        medicineTimeDrawer.drawerMenuButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_homeFragment_to_dateTimeSettingFragment)
        }

        val phoneNumberDrawer = binding.phoneNumberDrawer.binding
        phoneNumberDrawer.drawerMenuButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_homeFragment_to_phoneNumberSettingFragment)
        }

        val deviceStatusDrawer = binding.deviceStatusDrawer.binding
        deviceStatusDrawer.drawerMenuButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_homeFragment_to_deviceStatusFragment)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
