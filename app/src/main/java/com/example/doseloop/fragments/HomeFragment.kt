package com.example.doseloop.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.doseloop.R
import com.example.doseloop.viewmodel.HomeFragmentViewModel
import com.example.doseloop.databinding.FragmentHomeBinding

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

        val statusDrawer = binding.statusDrawer.binding
        statusDrawer.drawerMenuButton.setOnClickListener {
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