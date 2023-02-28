package com.example.doseloop.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.doseloop.R
import com.example.doseloop.viewmodel.HomeFragmentViewModel
import com.example.doseloop.databinding.FragmentHomeBinding


class HomeFragment : AbstractFragment<HomeFragmentViewModel>() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addViewModel(HomeFragmentViewModel())

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.navigateToDrawerExampleButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_drawerExampleFragment)
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}