package com.example.doseloop.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import androidx.navigation.fragment.findNavController
import com.example.doseloop.R
import com.example.doseloop.databinding.FragmentDateTimeSettingBinding
import com.example.doseloop.viewmodel.DateTimeSettingViewModel


private const val TAG = "MainActivity"

/**
 * Fragment for setting the date and time of taking medicine
 */
class DateTimeSettingFragment : AbstractFragment<DateTimeSettingViewModel>(
    DateTimeSettingViewModel()
) {

    private var _binding: FragmentDateTimeSettingBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDateTimeSettingBinding.inflate(inflater, container, false)

        val view = binding.root

        /// 1
//        addTextChangedListener(binding.timeEditText, binding.time1SubmitButton)
//        addTextChangedListener(binding.time2EditText, binding.time2SubmitButton)
//        addTextChangedListener(binding.time3EditText, binding.time3SubmitButton)
//        addTextChangedListener(binding.time4EditText, binding.time4SubmitButton)
//        addTextChangedListener(binding.time5EditText, binding.time5SubmitButton)

        /// 2
        addSeekBarChangeListener(binding.daySlider)
        addSeekBarChangeListener(binding.day2Slider)
        addSeekBarChangeListener(binding.day3Slider)
        addSeekBarChangeListener(binding.day4Slider)
        addSeekBarChangeListener(binding.day5Slider)
        addSeekBarChangeListener(binding.day6Slider)

        /// 3


        binding.backHomeButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_dateTimeSettingFragment_to_homeFragment)
        }

        return view
    }


//    private fun addTextChangedListener(editText: EditText, submitButton: Button){
//        editText.addTextChangedListener(object : TextWatcher {
//
//        })
//    }

    // SeekBar
    private fun addSeekBarChangeListener(seekBar: SeekBar?) {
        seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "")
                if (progress == 0) {
//                    binding.setDay2Text.text = "0"
                } else {
//                    binding.setDay2Text.text = "1"
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun addSubmitButtonListener() {
    }

    private fun addDeleteButtonListener() {

    }

    private fun handleTextErrors(button: Button, text: CharSequence, til: EditText) {
        var error: Boolean

        if (text.toString().trim().length > 5) {
            til.error = getString(R.string.set_right_time)
            error = true
        } else {
            til.error = null
            error = false
        }

        button.isEnabled = !error
    }

}