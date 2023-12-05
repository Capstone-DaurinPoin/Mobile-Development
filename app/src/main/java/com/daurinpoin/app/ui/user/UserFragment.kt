package com.daurinpoin.app.ui.user

import android.content.Intent
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val user: FirebaseUser? = auth.currentUser
        val textNama = view.findViewById<TextView>(R.id.textNama)
        val textEmail = view.findViewById<TextView>(R.id.textEmail)
        val imageUser = view.findViewById<ImageView>(R.id.imageUser)
        val layoutProfileSetting = view.findViewById<LinearLayout>(R.id.layoutProfileSetting)
        val layoutSignOut = view.findViewById<LinearLayout>(R.id.layoutSignOut)

        val displayName = user?.displayName
        if (!displayName.isNullOrBlank()) {
            // Set nama dan email dari Firebase
            textNama.text = displayName
            textEmail.text = user?.email
        } else {
            // Jika nama pengguna tidak tersedia, mungkin tampilkan pesan alternatif atau atur ke nilai default
            textNama.text = "Nama Pengguna Tidak Tersedia"
            textEmail.text = user?.email
        }

        // Implementasi logika untuk mengambil dan menampilkan gambar dari Firebase
        // Misalnya, menggunakan Firebase Storage atau URL gambar dari Firebase Authentication.
        // Gambar dapat diambil dan ditetapkan ke ImageView menggunakan library seperti Glide atau Picasso.
        // Contoh menggunakan Glide:
        user?.photoUrl?.let { photoUrl ->
            Glide.with(this).load(photoUrl).into(imageUser)
        }

        // Setel listener untuk tombol Profile Setting
        layoutProfileSetting.setOnClickListener {
            // Logika untuk menangani ketika Profile Setting di klik
            // Misalnya, buka halaman pengaturan profil.
        }

        layoutSignOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish()

            Toast.makeText(activity, "Sign out success!", Toast.LENGTH_SHORT).show()
        }
    }
}
