package com.example.doseloop.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}