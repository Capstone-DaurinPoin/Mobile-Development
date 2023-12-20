package com.daurinpoin.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.daurinpoin.app.ui.login.LoginActivity
import com.daurinpoin.app.ui.onboarding.OnboardingActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        sharedPreferences = getSharedPreferences("onboarding", Context.MODE_PRIVATE)

        // Tampilkan splash screen selama beberapa detik
        Handler().postDelayed({
            // Mengecek otentikasi pengguna
            if (isLoggedIn()) {
                navigateToHome()
            } else {
                if (!sharedPreferences.getBoolean("onboarding_complete", false)) {
                    startActivity(Intent(this, OnboardingActivity::class.java))
                } else {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }
            finish()
        }, SPLASH_TIME_OUT)
    }

    private fun isLoggedIn(): Boolean {
        val sharedPreferences = getSharedPreferences("login_status", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("isLoggedIn", false)
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val SPLASH_TIME_OUT: Long = 2000
    }
}



