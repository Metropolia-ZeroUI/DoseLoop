package com.example.doseloop.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.doseloop.viewmodel.MessageFragmentViewModel

/**
 * Example usage of AbstractFragment. Add ViewModel type to the diamond brackets, as displayed below.
 */
class MessageFragment : AbstractFragment<MessageFragmentViewModel>() {

    override fun fragmentOnCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        /**
         * Add view model
         */
        addViewModel(MessageFragmentViewModel())

        TODO("Not yet implemented")

    }

    override fun fragmentOnCreate(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }


}