package com.example.doseloop.fragments

import android.os.SystemClock
import androidx.fragment.app.Fragment
import com.example.doseloop.viewmodel.AbstractViewModel

/**
 * Fragment parent class. Common fragment functionality can be added here, if needed.
 * ViewModel type must be declared when inheriting this.
 *
 * How to access the ViewModel: viewModel.
 */
abstract class AbstractFragment<T: AbstractViewModel?>(protected val viewModel : T? = null) : Fragment() {
    private var lastDepClick = 0L
    fun preventButtonClickSpam(f: () -> Unit) {
        if (SystemClock.elapsedRealtime() - lastDepClick > 1000) {
            lastDepClick = SystemClock.elapsedRealtime()
            f()
        }
    }
}