package com.daurinpoin.app.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.daurinpoin.app.R
import com.daurinpoin.app.service.ApiClient
import com.daurinpoin.app.ui.login.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val nameEditText: EditText = findViewById(R.id.fullname)
        val emailEditText: EditText = findViewById(R.id.username)
        val passwordEditText: EditText = findViewById(R.id.password)
        val reenterPasswordEditText: EditText = findViewById(R.id.repassword)
        val registerButton: Button = findViewById(R.id.signupButton)

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val reenterPassword = reenterPasswordEditText.text.toString()

            if (password != reenterPassword) {
                Toast.makeText(applicationContext, "Password tidak cocok", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val registerResponse = ApiClient.apiService.register(name, email, password)
                    // Handle respon registrasi yang berhasil di sini
                    if (registerResponse.status == "success") {
                        Toast.makeText(
                            applicationContext, "Registrasi berhasil", Toast.LENGTH_SHORT
                        ).show()
                        navigateToLogin()
                    } else {
                        // Handle respon registrasi yang tidak berhasil
                        Toast.makeText(
                            applicationContext,
                            "Registrasi gagal: ${registerResponse.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    // Handle kegagalan koneksi atau request
                    Toast.makeText(applicationContext, "Terjadi kesalahan", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }
}
