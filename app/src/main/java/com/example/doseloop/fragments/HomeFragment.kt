package com.example.doseloop.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.doseloop.R
import com.example.doseloop.viewmodel.HomeFragmentViewModel


class HomeFragment : AbstractFragment<HomeFragmentViewModel>() {

    override fun fragmentOnCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        addViewModel(HomeFragmentViewModel())

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}