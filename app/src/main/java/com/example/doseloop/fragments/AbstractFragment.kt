package com.example.doseloop.fragments

import android.os.SystemClock
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.doseloop.R
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

    /**
     * Setup the toolbar in the fragment. An optional function can be added to override onBackPressed.
     */
    fun setToolBarBackButton(tb: androidx.appcompat.widget.Toolbar, f: (() -> Unit)? = null) {
        tb.setNavigationIcon(R.drawable.ic_back)
        tb.setNavigationOnClickListener {
            if (f != null) f()
            else activity?.onBackPressed()
        }
    }
}