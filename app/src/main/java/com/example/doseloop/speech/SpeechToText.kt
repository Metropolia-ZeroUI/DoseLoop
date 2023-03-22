package com.example.doseloop.speech

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.fragment.app.Fragment
import java.util.*

class SpeechToText(context: Context, private val listener: SpeechListener) {

    private val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
    }

    private val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            .apply { setRecognitionListener(listener) }

    fun tryRecognize(context: Fragment, consumer: ((String) -> Unit)? = null) {
        speechRecognizer.startListening(intent)
        context.startActivity(intent)
        listener.resultsConsumer = consumer
    }

    fun clean() {
        this.speechRecognizer.destroy()
    }
}