package com.example.doseloop.fragments

import android.content.res.ColorStateList
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment
import com.example.doseloop.R
import com.example.doseloop.speech.SpeechToText
import com.example.doseloop.util.DATE_TIME_1
import com.example.doseloop.util.DEVICE_USER_NUMBER
import com.example.doseloop.util.NOTIFICATION_TEXT_MAX_LENGTH
import com.example.doseloop.viewmodel.AbstractViewModel
import com.example.doseloop.viewmodel.DateTimeSettingViewModel
import com.google.android.material.textfield.TextInputLayout

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

    /**
     * Make sure input is only max 10 characters, there's no country code added, that the first number is a zero and that there are no other special characters.
     * The + check is done separately as we want a separate error message for possible country codes.
     */
    private fun handleTextErrors(button: Button, text: CharSequence, til: TextInputLayout) {
        var error: Boolean
        val isDigit = text.toString().trim().isDigitsOnly()

        if (text.toString().trim().length > 10) {
            til.error = getString(R.string.error_too_long_number)
            error = true
        } else if (text.toString().trim().contains('+')) {
            til.error = getString(R.string.error_country_code)
            error = true
        } else if (text.toString().trim().isNotEmpty() && text.toString().trim()[0] != '0') {
            til.error = getString(R.string.error_invalid_first_number)
            error = true
        } else if (!isDigit) {
            til.error = getString(R.string.error_not_digit)
            error = true
        } else {
            til.error = null
            error = false
        }

        // Disable button but no error if field is empty
        if (text.toString().trim().isEmpty()) {
            error = true
        }

        button.isEnabled = !error
    }

    fun addTextChangedListener(editText: EditText, submitButton: Button, til: TextInputLayout) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val charSequence = p0 ?: ""
                handleTextErrors(submitButton, charSequence, til)
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun handleStringTextErrors(button: Button, text: CharSequence, til: TextInputLayout) {
        var error: Boolean

        if (text.toString().trim().length > NOTIFICATION_TEXT_MAX_LENGTH) {
            til.error = getString(R.string.error_too_long_text)
            error = true
        } else {
            til.error = null
            error = false
        }

        // Disable button but no error if field is empty
        if (text.toString().trim().isEmpty()) {
            error = true
        }
        button.isEnabled = !error
    }

    fun addStringTextChangedListener(editText: EditText, submitButton: Button, til: TextInputLayout) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val charSequence = p0 ?: ""
                handleStringTextErrors(submitButton, charSequence, til)
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }


    /**
     * Setup the functionality of speech recognizer in a TextInputLayout.
     */
    fun addRecordVoiceButtonListener(til: TextInputLayout, editText: EditText, speechToText: SpeechToText, position: String) {
        til.setEndIconOnClickListener {
            if(til.tag == "2") {
                speechToText.stopListening()
                til.setEndIconDrawable(R.drawable.ic_mic)
                til.setEndIconTintList(ColorStateList.valueOf(resources.getColor(R.color.dark_gray)))
                editText.hint = ""
                editText.clearFocus()
                if (position.toInt() < 6) {
                    editText.setText(viewModel?.getFromPrefs("PHONE_NUMBER_${position}", ""))
                } else {
                    editText.setText(viewModel?.getFromPrefs(DEVICE_USER_NUMBER, ""))
                }

                til.tag = "1"
            }
            else {
                var userText = ""
                til.tag = "2"
                til.setEndIconDrawable(R.drawable.ic_mic_record)
                til.setEndIconTintList(ColorStateList.valueOf(resources.getColor(R.color.wine_red)))
                editText.setText("")
                editText.hint = getString(R.string.kuunnellaan)
                editText.requestFocus()
                speechToText.tryRecognize(this) {
                    userText = it
                    til.setEndIconDrawable(R.drawable.ic_mic)
                    til.setEndIconTintList(ColorStateList.valueOf(resources.getColor(R.color.dark_gray)))
                    if (userText != "") {
                        userText = userText.replace(" ", "")
                        if(userText.trim()[0] == '0') {
                            editText.setText(userText)
                            editText.hint = ""
                            editText.clearFocus()
                        } else {
                            val toast = Toast.makeText(activity, "'$userText' ei ole sopiva puhelinnumero", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                            editText.hint = ""
                            editText.clearFocus()
                            editText.setText(viewModel?.getFromPrefs("PHONE_NUMBER_${position}", ""))
                        }
                        til.setEndIconDrawable(R.drawable.ic_mic)
                        til.setEndIconTintList(ColorStateList.valueOf(resources.getColor(R.color.dark_gray)))
                        til.tag = "1"
                    }
                }
            }
        }
    }

    fun addRecordVoiceButtonTextListener(til: TextInputLayout, editText: EditText, speechToText: SpeechToText, prefs: String) {
        til.setEndIconOnClickListener {
            if(til.tag == "2") {
                speechToText.stopListening()
                til.setEndIconDrawable(R.drawable.ic_mic)
                til.setEndIconTintList(ColorStateList.valueOf(resources.getColor(R.color.dark_gray)))
                editText.hint = ""
                editText.clearFocus()
                editText.setText(viewModel?.getFromPrefs("$prefs", ""))
                til.tag = "1"
            }
            else {
                var userText = ""
                til.tag = "2"
                til.setEndIconDrawable(R.drawable.ic_mic_record)
                til.setEndIconTintList(ColorStateList.valueOf(resources.getColor(R.color.wine_red)))
                editText.setText("")
                editText.hint = getString(R.string.kuunnellaan)
                editText.requestFocus()
                speechToText.tryRecognize(this) {
                    userText = it
                    til.setEndIconDrawable(R.drawable.ic_mic)
                    til.setEndIconTintList(ColorStateList.valueOf(resources.getColor(R.color.dark_gray)))
                    if (userText != "") {
                        editText.setText(userText)
                        editText.hint = ""
                        editText.clearFocus()
                        til.setEndIconDrawable(R.drawable.ic_mic)
                        til.setEndIconTintList(ColorStateList.valueOf(resources.getColor(R.color.dark_gray)))
                        til.tag = "1"
                    }
                }
            }
        }
    }

    fun addRecordVoiceButtonTimeListener(til: ImageButton, editText: AppCompatTextView, speechToText: SpeechToText, position: String) {
        til.setOnClickListener {
            if(til.tag == "2") {
                speechToText.stopListening()
                til.setImageResource(R.drawable.ic_mic)
                editText.hint = ""
                editText.clearFocus()
                editText.setText(viewModel?.getFromPrefs("DATE_TIME_${position}", "0:00"))
                til.tag = "1"
            }
            else {
                var userText = ""
                til.tag = "2"
                til.setImageResource(R.drawable.ic_mic_record)
                editText.setText("")
                editText.hint = "..."
                editText.requestFocus()
                speechToText.tryRecognize(this) {
                    userText = it
                    til.setImageResource(R.drawable.ic_mic)
                    if (userText != "" && userText.replace(".", "").isDigitsOnly() && userText.replace(".", "").length <= 4) {
                        if(userText.length === 3) {
                            val reformat = userText.replaceFirst("${userText[0]}", "${userText[0]}:" )
                            editText.setText(reformat)
                            editText.hint = ""
                            editText.clearFocus()
                        } else {
                            userText = userText.replace(".", ":")
                            editText.setText(userText)
                            editText.hint = ""
                            editText.clearFocus()
                        }
                    }
                    else {
                            val toast = Toast.makeText(activity, "'$userText' ei ole sopiva kellonaika", Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                            editText.hint = ""
                            editText.clearFocus()
                            editText.setText(viewModel?.getFromPrefs("DATE_TIME_${position}", "0:00"))
                        }
                    til.setImageResource(R.drawable.ic_mic)
                    til.tag = "1"
                }
            }
        }
    }
}