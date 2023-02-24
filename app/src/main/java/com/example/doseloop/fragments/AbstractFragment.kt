package com.example.doseloop.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.doseloop.viewmodel.AbstractViewModel

/**
 * Fragment parent class. Common fragment functionality can be added here, if needed.
 */
abstract class AbstractFragment<T: AbstractViewModel?> : Fragment() {

    protected var viewModel : T? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentOnCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return fragmentOnCreateView(inflater, container, savedInstanceState)
    }

    /**
     * Gets called on the onCreateView, override this and put onCreateView logic here
     */
    abstract fun fragmentOnCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View

    /**
     * Gets called on the onCreate, override this and put onCreate logic here
     */
    abstract fun fragmentOnCreate(savedInstanceState: Bundle?)

    /**
     * Adds the given ViewModel.
     */
    fun addViewModel(viewModel: T) {
        this.viewModel = viewModel
    }
}