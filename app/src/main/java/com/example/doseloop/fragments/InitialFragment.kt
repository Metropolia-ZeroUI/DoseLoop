package com.example.doseloop.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.doseloop.R
import com.example.doseloop.databinding.FragmentInitialBinding
import com.example.doseloop.util.DEVICE_PHONE_NUMBER
import com.example.doseloop.util.INITIAL_LAUNCH
import com.example.doseloop.viewmodel.InitialFragmentViewModel

/**
 * Initial screen to display to get the device's phone number.
 *
 * TODO: Add voice recognition for number entry
 */

class InitialFragment : AbstractFragment<InitialFragmentViewModel>(InitialFragmentViewModel()) {

    private var _binding: FragmentInitialBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (viewModel?.getFromPrefs(INITIAL_LAUNCH, false) == true) {
            this.findNavController().navigate(R.id.action_initialFragment_to_homeFragment)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInitialBinding.inflate(inflater, container, false)

        binding.initialSubmitButton.setOnClickListener {
            val phoneNumber = binding.editTextPhone.text.toString()
            viewModel?.saveToPrefs(DEVICE_PHONE_NUMBER, phoneNumber)
            viewModel?.saveToPrefs(INITIAL_LAUNCH, true)
            it.findNavController().navigate(R.id.action_initialFragment_to_homeFragment)
        }

        return binding.root
    }
}