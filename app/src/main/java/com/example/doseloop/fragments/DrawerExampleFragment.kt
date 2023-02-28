package com.example.doseloop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.doseloop.R
import com.example.doseloop.databinding.FragmentDrawerExampleBinding

/**
 * Example fragment, demonstrating the usage of the DrawerMenu component.
 */

class DrawerExampleFragment : Fragment() {

    private var _binding: FragmentDrawerExampleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDrawerExampleBinding.inflate(inflater, container, false)
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
            val toast = Toast.makeText(context, "This is another example!", Toast.LENGTH_SHORT)
            toast.show()
        }

        val searchDrawer = binding.searchDrawer.binding
        searchDrawer.drawerMenuButton.setOnClickListener {
            val toast = Toast.makeText(context, "This is a third example!", Toast.LENGTH_SHORT)
            toast.show()
        }

        binding.backToHomeButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_drawerExampleFragment_to_homeFragment)
        }

        return view
    }

}