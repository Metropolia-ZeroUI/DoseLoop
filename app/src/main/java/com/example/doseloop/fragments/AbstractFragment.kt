package com.example.doseloop.fragments

import androidx.fragment.app.Fragment
import com.example.doseloop.viewmodel.AbstractViewModel

/**
 * Fragment parent class. Common fragment functionality can be added here, if needed.
 * ViewModel type must be declared when inheriting this.
 *
 * How to access the ViewModel: viewModel.
 */
abstract class AbstractFragment<T: AbstractViewModel?>(protected val viewModel : T? = null) : Fragment() {

}