package com.daurinpoin.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.daurinpoin.app.R
import com.daurinpoin.app.ui.scan.ScanActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var btnScan: FrameLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val user: FirebaseUser? = auth.currentUser
        val textName: TextView = view.findViewById(R.id.textNamaHome)
        val displayName = user?.displayName
        val words = displayName?.split(" ")
        val firstName = words?.firstOrNull()

        textName.text = firstName


        btnScan = view.findViewById(R.id.btn_ScanTrash)
        btnScan.setOnClickListener {
            val intent = Intent(requireContext(), ScanActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}
