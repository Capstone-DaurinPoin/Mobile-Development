package com.daurinpoin.app.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.daurinpoin.app.MainActivity
import com.daurinpoin.app.R
import com.daurinpoin.app.ui.register.RegisterActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        FirebaseApp.initializeApp(this)

        auth = FirebaseAuth.getInstance()

        val emailEditText: EditText = findViewById(R.id.editTextEmailLogin)
        val passwordEditText: EditText = findViewById(R.id.editTextPasswordLogin)
        val loginButton: Button = findViewById(R.id.btnLogin)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                navigateToHome()
            } else {
                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }

    fun signUp(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}
