package com.example.doseloop.speech

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer

class SpeechListener(val onSuccess: ((String)->Unit)? = null,
                     val onError: (()->Unit)? = null,
                     val onReady: (()->Unit)? = null,
                     val onEnd: (()->Unit)? = null)
    : RecognitionListener {
    override fun onReadyForSpeech(p0: Bundle?) = onReady?.invoke() ?: Unit
    override fun onBeginningOfSpeech() {}
    override fun onRmsChanged(p0: Float) {}
    override fun onBufferReceived(p0: ByteArray?) {}
    override fun onEndOfSpeech() = onEnd?.invoke()  ?: Unit
    override fun onError(err: Int) = onError?.invoke() ?: Unit
    override fun onPartialResults(resBundle: Bundle?) {}
    override fun onEvent(p0: Int, p1: Bundle?) {}
    override fun onResults(resBundle: Bundle?) {
        val res = resBundle?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.get(0) as String
        resultsConsumer?.invoke(res)
        onSuccess?.invoke(res)
    }
    var resultsConsumer: ((String) -> Unit)? = null
}