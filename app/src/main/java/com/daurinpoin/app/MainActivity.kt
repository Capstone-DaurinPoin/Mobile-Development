package com.daurinpoin.app

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.daurinpoin.app.databinding.ActivityMainBinding
import com.daurinpoin.app.ui.scan.ScanActivity
import me.ibrahimsn.lib.SmoothBottomBar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var bottomBar: SmoothBottomBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.main_fragment)
        bottomBar = binding.bottomBar

        getPermission()
        setupBottomMenu()
    }

    private fun setupBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav_menu)
        val menu = popupMenu.menu

        bottomBar.setupWithNavController(menu, navController)

        bottomBar.onItemSelected = { index ->
            when (index) {
                2 -> {
                    val intent = Intent(this, ScanActivity::class.java)
                    startActivityForResult(intent, SCAN_REQUEST_CODE)
                    finish()
                }
            }
        }

        bottomBar.onItemReselected = { index ->
            when (index) {
                2 -> {
                    val intent = Intent(this, ScanActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SCAN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Periksa apakah harus menavigasi ke HomeFragment
            val shouldNavigateToHome = data?.getBooleanExtra("navigateToHome", false) ?: false
            if (shouldNavigateToHome) {
                navController.navigate(R.id.navigation_home)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun getPermission() {
        if (ContextCompat.checkSelfPermission(
                this, android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 101)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            getPermission()
        }
    }

    companion object {
        private const val SCAN_REQUEST_CODE = 123 // Gantilah dengan nilai request code yang sesuai
    }
}