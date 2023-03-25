package com.example.doseloop.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navArgs
import com.example.doseloop.PopupActivity
import com.example.doseloop.R
import com.example.doseloop.databinding.ActivityConfirmPhoneNumberChangeBinding
import com.example.doseloop.databinding.ActivityPhoneNumberSettingUnsavedChangesBinding
import com.example.doseloop.fragments.PhoneNumberSettingFragmentDirections
import com.example.doseloop.util.NAVIGATE_TO_HOME_FRAGMENT
import com.example.doseloop.viewmodel.PhoneNumberSettingViewModel

class PhoneNumberSettingUnsavedChangesActivity : PopupActivity<PhoneNumberSettingViewModel>(PhoneNumberSettingViewModel()) {

    private lateinit var binding: ActivityPhoneNumberSettingUnsavedChangesBinding
    private val args : PhoneNumberSettingUnsavedChangesActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        height = 900
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneNumberSettingUnsavedChangesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtons()
        binding.confirmWindowTitle.text = getString(R.string.unsaved_changes, args.unsavedChanges)
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