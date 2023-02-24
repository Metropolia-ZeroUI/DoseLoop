package com.example.doseloop.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doseloop.R
import com.example.doseloop.viewmodel.MessageFragmentViewModel

/**
 * Example usage of AbstractFragment. Add ViewModel type to the diamond brackets, as displayed below.
 */
class MessageFragment : AbstractFragment<MessageFragmentViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        addViewModel(MessageFragmentViewModel())

        return inflater.inflate(R.layout.fragment_message, container, false)

    }

}