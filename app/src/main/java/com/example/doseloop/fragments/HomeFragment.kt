package com.example.doseloop.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
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

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.navigateToDrawerExampleButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_drawerExampleFragment)
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}