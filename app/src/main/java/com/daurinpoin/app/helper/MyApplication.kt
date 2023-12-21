package com.daurinpoin.app.helper

import android.app.Application

class MyApplication : Application() {

    lateinit var authenticationManager: AuthenticationManager

    override fun onCreate() {
        super.onCreate()

        // Inisialisasi AuthenticationManager di sini
        authenticationManager = AuthenticationManager(this)
    }
}
