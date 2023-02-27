package com.example.doseloop.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.doseloop.viewmodel.AbstractViewModel

/**
 * Fragment parent class. Common fragment functionality can be added here, if needed.
 * ViewModel type must be declared when inheriting this.
 */
abstract class AbstractFragment<T: AbstractViewModel?> : Fragment() {

    protected var viewModel : T? = null
        private set

    /**
     * Use this to add a ViewModel of the declared type.
     */
    fun addViewModel(viewModel: T) {
        this.viewModel = viewModel
    }
}