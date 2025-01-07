package com.orbixstar.kmpapp

import android.app.Application
import com.orbixstar.kmpapp.di.initKoin

class KMPApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
