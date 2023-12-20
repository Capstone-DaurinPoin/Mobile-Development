package com.daurinpoin.app.ui.user

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.daurinpoin.app.R
import com.daurinpoin.app.ui.login.LoginActivity

class UserFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var textName: TextView
    private lateinit var textEmail: TextView
    private lateinit var imageUser: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("login_status", Context.MODE_PRIVATE)
        textName = view.findViewById(R.id.textNama)
        textEmail = view.findViewById(R.id.textEmail)
        imageUser = view.findViewById(R.id.imageUser)

        val layoutProfileSetting = view.findViewById<LinearLayout>(R.id.layoutProfileSetting)
        val layoutSignOut = view.findViewById<LinearLayout>(R.id.layoutSignOut)

        layoutSignOut.setOnClickListener {
            performLogout()
            Toast.makeText(activity, "Sign out success!", Toast.LENGTH_SHORT).show()
        }

        loadUser()
    }

    private fun loadUser() {
        val sharedPreferences = requireContext().getSharedPreferences("user_data", Context.MODE_PRIVATE)

        val name = sharedPreferences.getString("nama", "")
        val email = sharedPreferences.getString("email", "")
        val imageUser = sharedPreferences.getString("imageUrl", "")

        textName.text = name
        textEmail.text = email
    }

    private fun performLogout() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()

        val intent = Intent(requireContext(), LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        requireActivity().finish()
    }
}
