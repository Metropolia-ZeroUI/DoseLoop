package com.example.doseloop.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.doseloop.R
import com.example.doseloop.databinding.FragmentHomeBinding
import com.example.doseloop.util.*
import com.example.doseloop.viewmodel.DateTimeSettingViewModel
import com.example.doseloop.viewmodel.DeviceStatusViewModel
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatterBuilder
import com.example.doseloop.viewmodel.HomeFragmentViewModel


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

        // Time and day
        val formatter = DateTimeFormatterBuilder()
            .appendPattern("HH:mm")
            .toFormatter()
        val currentTime = LocalTime.now()

        val viewModel = ViewModelProvider(this).get(DateTimeSettingViewModel::class.java)

        val inputList = listOf(DATE_TIME_1, DATE_TIME_2, DATE_TIME_3, DATE_TIME_4, DATE_TIME_5, DATE_TIME_6)
        val inputTimeList = mutableListOf<LocalTime>()
        inputList.forEach { key ->
            viewModel.getFromPrefs(key, "")
                .takeIf { it!!.isNotBlank() }
                ?.let { LocalTime.parse(it, formatter) }
                ?.takeIf { !it.isBefore(currentTime) }
                ?.let { inputTimeList.add(it) }
        }

        // Sort the list based on time difference
        val sortedInputDataList = inputTimeList.sortedBy { Duration.between(it, currentTime).abs() }

        // Choose the one with the smallest time difference
        val closestInputData = sortedInputDataList.firstOrNull()

        // Show the closest input data
        binding.tvTime.text = closestInputData?.format(formatter) ?: getString(R.string.set_format)

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