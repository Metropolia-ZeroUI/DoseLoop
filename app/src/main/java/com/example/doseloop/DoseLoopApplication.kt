package com.example.doseloop

import android.app.Application

class DoseLoopApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: DoseLoopApplication
            private set
    }
}