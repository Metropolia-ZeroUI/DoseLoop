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

        /**
         * Example on how to set onClickListener for drawer buttons. Drawers have their own bindings so it's easy to
         * retrieve components and add functionality to them.
         */

        val statusDrawer = binding.statusDrawer.binding
        statusDrawer.drawerMenuButton.setOnClickListener {
            val toast = Toast.makeText(context, "This is an example!", Toast.LENGTH_SHORT)
            toast.show()
        }

        val reminderDrawer = binding.reminderDrawer.binding
        reminderDrawer.drawerMenuButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_homeFragment_to_phoneNumberSettingFragment)
        }

        val searchDrawer = binding.searchDrawer.binding
        searchDrawer.drawerMenuButton.setOnClickListener {
            val toast = Toast.makeText(context, "This is a third example!", Toast.LENGTH_SHORT)
            toast.show()
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}