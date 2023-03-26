package com.example.doseloop

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.doseloop.viewmodel.AbstractViewModel

/**
 * Creates a popup window on top of the current Activity/Fragment.
 * To properly enable popup window functionality (i.e. closing window when pressing outside the window),
 * add to AndroidManifest.xml the following property to the chosen Activity
 * (See ConfirmPhoneNumberChangeActivity as an example):
 *      android:theme="@style/Theme.DoseLoop.PopupTheme"
 */
abstract class PopupActivity<T: AbstractViewModel?>(protected val viewModel : T? = null) : AppCompatActivity() {

    /**
     * Determines the width of the popup window, i.e. 0.9 = 90% window width
     * */
    var widthMultiplier: Double = 0.9

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val width = metrics.widthPixels
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window.attributes.dimAmount = 0.7f
        window.setLayout((width * widthMultiplier).toInt(), 650)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}