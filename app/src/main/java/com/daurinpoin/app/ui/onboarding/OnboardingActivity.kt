package com.daurinpoin.app.ui.onboarding

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.daurinpoin.app.R
import com.daurinpoin.app.ui.login.LoginActivity

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

    }
    fun goToLoginPage(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        setOnboardingComplete()
    }

    private fun setOnboardingComplete() {
        val sharedPreferences = getSharedPreferences("onboarding", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("onboarding_complete", true)
        editor.apply()
    }
}