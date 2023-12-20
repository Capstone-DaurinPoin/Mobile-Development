package com.daurinpoin.app.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.daurinpoin.app.MainActivity
import com.daurinpoin.app.R
import com.daurinpoin.app.response.LoginResponse
import com.daurinpoin.app.response.LoginResult
import com.daurinpoin.app.service.ApiClient
import com.daurinpoin.app.ui.register.RegisterActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText: EditText = findViewById(R.id.editTextEmailLogin)
        val passwordEditText: EditText = findViewById(R.id.editTextPasswordLogin)
        val loginButton: Button = findViewById(R.id.btnLogin)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val loginResponse = ApiClient.apiService.login(email, password)
                    handleLoginResponse(loginResponse)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun handleLoginResponse(loginResponse: LoginResponse) {
        if (loginResponse.status == "success") {
            // Login berhasil, simpan data pengguna dan navigasi ke layar utama
            saveUserData(loginResponse.data)
            saveLoginStatus(true)
            Toast.makeText(applicationContext, "Login berhasil", Toast.LENGTH_SHORT).show()
            navigateToHome()
        } else {
            // Login gagal
            Toast.makeText(
                applicationContext, "Login gagal: ${loginResponse.message}", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun saveUserData(loginResult: LoginResult?) {
        loginResult?.let {
            val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("nama", it.nama ?: "")
            editor.putString("imageUrl", it.imageUrl ?: "")
            editor.putString("email", it.email ?: "")
            editor.putInt("point", it.point ?: 0)
            editor.apply()
        }
    }

    private fun saveLoginStatus(isLoggedIn: Boolean) {
        val sharedPreferences = getSharedPreferences("login_status", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", isLoggedIn)
        editor.apply()
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000) // You can adjust the time threshold here (in milliseconds)
    }

    fun signUp(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}
