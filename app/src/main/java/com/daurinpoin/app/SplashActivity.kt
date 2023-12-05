package com.daurinpoin.app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.daurinpoin.app.ui.login.LoginActivity
import com.daurinpoin.app.ui.onboarding.OnboardingActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("onboarding", Context.MODE_PRIVATE)

        // Tampilkan splash screen selama beberapa detik
        Handler().postDelayed({
            // Mengecek otentikasi pengguna
            if (auth.currentUser != null) {
                // Jika pengguna sudah masuk, langsung ke HomeActivity
                navigateToHome()
            } else {
                // Jika belum masuk dan belum melihat halaman onboarding
                if (!sharedPreferences.getBoolean("onboarding_complete", false)) {
                    // Tampilkan halaman onboarding
                    startActivity(Intent(this, OnboardingActivity::class.java))
                    finish()
                } else {
                    // Jika sudah melihat halaman onboarding, pindah ke LoginActivity
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
        }, SPLASH_TIME_OUT)
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val SPLASH_TIME_OUT: Long =
            2000 // Waktu tunda splash screen dalam milidetik (2 detik dalam contoh ini)
    }
}



