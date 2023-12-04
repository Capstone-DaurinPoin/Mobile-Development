package com.daurinpoin.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.daurinpoin.app.ui.onboarding.OnboardingActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIMEOUT: Long = 2000 // Durasi tampilan splash screen dalam milidetik

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val mainIntent = Intent(
                this@SplashActivity,
                OnboardingActivity::class.java
            ) // Ganti dengan activity utama Anda
            startActivity(mainIntent)
            finish()
        }, SPLASH_TIMEOUT)
    }
}

