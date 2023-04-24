package com.example.doseloop.fragments

import android.os.Bundle
import android.util.Log
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
import com.example.doseloop.viewmodel.HomeFragmentViewModel
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

        /**
         * First version
         *
         */

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val currentTime = LocalTime.now(ZoneId.systemDefault())
        val currentDate = LocalDate.now(ZoneId.systemDefault())

        val viewModel = ViewModelProvider(this).get(DateTimeSettingViewModel::class.java)

        val inputList = listOf(
            Triple(DATE_KEY_1, DATE_TIME_1, DAY_1),
            Triple(DATE_KEY_2, DATE_TIME_2, DAY_2),
            Triple(DATE_KEY_3, DATE_TIME_3, DAY_3),
            Triple(DATE_KEY_4, DATE_TIME_4, DAY_4),
            Triple(DATE_KEY_5, DATE_TIME_5, DAY_5),
            Triple(DATE_KEY_6, DATE_TIME_6, DAY_6)
        )

        val inputTimeList = mutableListOf<Triple<LocalDateTime, LocalTime, Boolean>>()
        inputList.forEach { (dateKey, timeKey, dayKey) ->
            val savedDate = viewModel.getFromPrefs(dateKey, "")
            val savedTime = viewModel.getFromPrefs(timeKey, "")
            val savedDateTime = if (savedDate?.isNotEmpty() == true && savedTime?.isNotEmpty() == true) {
                LocalDateTime.parse("$savedDate ${savedTime.padStart(5, '0')}", formatter)
            } else {
                null
            }

            val isEveryday = viewModel.getFromPrefs(dayKey, false)
            savedDateTime?.let { dateTime ->
                var nextDateTime = dateTime
                val daysUntilNext = ChronoUnit.DAYS.between(currentDate, dateTime.toLocalDate()).toInt()
                if (isEveryday) {
                    if (daysUntilNext <= 0) {
                        nextDateTime = dateTime.plusDays(2)
                    } else if (daysUntilNext == 1) {
                        nextDateTime = dateTime.plusDays(1)
                    }
                }
                inputTimeList.add(Triple(nextDateTime, nextDateTime.toLocalTime(), isEveryday))
            }
        }

        val sortedInputDataList = inputTimeList.sortedWith(compareBy(
            // First, sort by the closest upcoming time
            { ChronoUnit.MINUTES.between(LocalDateTime.now(), it.first) },
            // Next sort by true or false.
            // If the item is false and goes to the present time, move it to the end of the list.
            // If the item is true, count how many days are left to show,
            // if there are no other nearer times in that interval, show that, if there are others, move it to the end of the list.
            { if (!it.third && it.first.isBefore(LocalDateTime.now())) Int.MAX_VALUE else if (it.third) ChronoUnit.DAYS.between(currentDate, it.first.toLocalDate()) else -1 },
            { Duration.between(currentTime, it.second) }
        ))

        Log.d("TAG", "Sorted input data list:")
        sortedInputDataList.forEach {
            Log.d("inputlis", "${it.first} ${it.second} ${it.third}")
        }

        sortedInputDataList.firstOrNull()?.let { (dateTime, time) ->
            binding.tvTime.text = time.format(DateTimeFormatter.ofPattern("HH:mm"))
            Log.d("Listsorted", "$sortedInputDataList")

            var daysUntilNext = ChronoUnit.DAYS.between(currentDate, dateTime.toLocalDate()).toInt()
            if (daysUntilNext < 0) {
                daysUntilNext = 0
            }
            Log.d("Listsorted", "$daysUntilNext")
            val dayText = when {
                daysUntilNext == 0 -> getString(R.string.set_today)
                daysUntilNext == 1 -> getString(R.string.set_tomorrow)
                daysUntilNext == 2 -> getString(R.string.set_after_tomorrow)
                daysUntilNext > 2 -> dateTime.format(DateTimeFormatter.ofPattern("EEE"))
                else -> getString(R.string.unknown_day)
            }
            binding.tvDay.text = dayText
        }
        // if the list is empty
        if (sortedInputDataList.isEmpty()) {
            binding.tvTime.text = getString(R.string.set_format)
            binding.tvDay.text = getString(R.string.unknown_day)
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