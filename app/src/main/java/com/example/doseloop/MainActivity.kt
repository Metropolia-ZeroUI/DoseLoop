package com.example.doseloop

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.doseloop.comms.impl.SmsMessageService
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private var speechRecognizer: SpeechRecognizer? = null
    private var editText: EditText? = null
    private var micBtn: ImageView? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SmsMessageService.checkPermissions(this);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            checkPermission()
        }

        editText = findViewById(R.id.edittext)
        micBtn = findViewById(R.id.micbutton)
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)

        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        speechRecognizer!!.setRecognitionListener(object : RecognitionListener{
            override fun onReadyForSpeech(p0: Bundle?) {
            }

            override fun onBeginningOfSpeech() {
                editText!!.setText("")
                editText!!.setHint("Listening")
            }

            override fun onRmsChanged(p0: Float) {
            }

            override fun onBufferReceived(p0: ByteArray?) {
            }

            override fun onEndOfSpeech() {
            }

            override fun onError(p0: Int) {
            }

            override fun onResults(bundle: Bundle?) {
                micBtn!!.setImageResource(R.drawable.ic_alarm)
                val data = bundle!!.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                editText!!.setText(data!![0])
            }

            override fun onPartialResults(p0: Bundle?) {
            }

            override fun onEvent(p0: Int, p1: Bundle?) {
            }
        })

        micBtn!!.setOnTouchListener { view, motionEvent ->

            if(motionEvent.action == MotionEvent.ACTION_UP) {
                Log.i(TAG,"nappi ylhääll")
                speechRecognizer!!.stopListening()
            }
            if(motionEvent.action == MotionEvent.ACTION_DOWN) {
                Log.i(TAG,"nappi alhaall")
                micBtn!!.setImageResource(R.drawable.ic_mic)
                speechRecognizer!!.startListening(speechRecognizerIntent)
            }

            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        speechRecognizer!!.destroy()
    }
    private fun checkPermission() {
        val requiredPermission = Manifest.permission.RECORD_AUDIO
        if (checkCallingOrSelfPermission(requiredPermission) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(requiredPermission), 1)
        }
    }

    // remove later
    var TAG = "test"
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.isNotEmpty()){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                Log.i(TAG, "granted")
            }
            else{
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                Log.i(TAG, "denied")
            }
        }
    }
}