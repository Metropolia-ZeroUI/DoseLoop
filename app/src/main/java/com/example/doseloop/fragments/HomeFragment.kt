package com.example.doseloop.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.doseloop.R
import com.example.doseloop.viewmodel.HomeFragmentViewModel
import com.example.doseloop.databinding.FragmentHomeBinding
import com.example.doseloop.speech.SpeechListener
import com.example.doseloop.speech.SpeechToText

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

        val speechToTxt = SpeechToText(requireContext(), listener = SpeechListener(
            onSuccess = { Log.i(SpeechListener::class.simpleName, "Speech got recognized") },
            onError = { Log.e(SpeechListener::class.simpleName, "Failed to recognize speech") },
            onReady = { Log.i(SpeechListener::class.simpleName, "Ready") },
            onEnd = { Log.d(SpeechListener::class.simpleName, "End")}
        ))

        /**
         * Example on how to set onClickListener for drawer buttons. Drawers have their own bindings so it's easy to
         * retrieve components and add functionality to them.
         */
        val statusDrawer = binding.statusDrawer.binding
        statusDrawer.drawerMenuButton.setOnClickListener {
            speechToTxt.tryRecognize(this) {
                Log.d(this::class.simpleName, "StatusDrawer result: $it")
            };
        }

        val reminderDrawer = binding.reminderDrawer.binding
        reminderDrawer.drawerMenuButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_homeFragment_to_phoneNumberSettingFragment)
        }

        val searchDrawer = binding.searchDrawer.binding
        searchDrawer.drawerMenuButton.setOnClickListener {
            speechToTxt.tryRecognize(this) {
                Log.d(this::class.simpleName, "SearchDrawer result: $it")
            };
        }

        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}