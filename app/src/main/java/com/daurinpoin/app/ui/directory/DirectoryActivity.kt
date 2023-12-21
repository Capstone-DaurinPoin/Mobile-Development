package com.daurinpoin.app.ui.directory

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daurinpoin.app.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DirectoryActivity : AppCompatActivity() {

    private val READ_EXTERNAL_STORAGE_PERMISSION_CODE = 1
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DirectoryAdapter
    private val imageLabelList: MutableList<LabelItem> = mutableListOf()

    companion object {
        private const val PREF_NAME = "DirectoryActivityPrefs"
        private const val KEY_IMAGE_LABEL_LIST = "key_image_label_list"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directory)

        recyclerView = findViewById(R.id.recyclerViewDirectory)
        adapter = DirectoryAdapter(imageLabelList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        itemTouchHelper.attachToRecyclerView(recyclerView)
        checkAndRequestPermission()
        loadDataFromPrefs()

//        val imagePath = intent.getStringExtra("imagePath")
        val label = intent.getStringExtra("label")
        val timestamp = intent.getStringExtra("timestamp")

        Log.d("DirectoryActivity", "imagePath: , label: $label, timestamp: $timestamp")

        // Add the new item to the list
        if (!label.isNullOrBlank() && !timestamp.isNullOrBlank()) {
            val newItem = LabelItem(null.toString(), label, timestamp)
            imageLabelList.add(newItem)
            adapter.notifyItemInserted(imageLabelList.size - 1)

            sendTotalItemCount(imageLabelList.size)
        }
    }

    private fun sendTotalItemCount(totalItemCount: Int) {
        val resultIntent = Intent()
        resultIntent.putExtra("totalItemCount", totalItemCount)
        setResult(Activity.RESULT_OK, resultIntent)
    }

    private fun loadDataFromPrefs() {
        val prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val savedJson = prefs.getString(KEY_IMAGE_LABEL_LIST, null)

        if (!savedJson.isNullOrBlank()) {
            val type = object : TypeToken<List<LabelItem>>() {}.type
            val savedList: List<LabelItem> = Gson().fromJson(savedJson, type)

            Log.d("DirectoryActivity", "Loaded items: $savedList")

            imageLabelList.addAll(savedList)
            sortItemsByTimestamp()
        }
    }

    private fun saveDataToPrefs() {
        val prefs = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val json = Gson().toJson(imageLabelList)
        editor.putString(KEY_IMAGE_LABEL_LIST, json)
        editor.apply()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveDataToPrefs()
    }

    val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
        0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            // Implementasi pengurutan di sini jika diperlukan
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            // Hapus item dari dataset
            val position = viewHolder.adapterPosition
            val deletedItem = imageLabelList.removeAt(position)
            adapter.notifyItemRemoved(position)

            showUndoToast(deletedItem, position)
        }
    })

    private fun showUndoToast(deletedItem: LabelItem, position: Int) {
        val undoToast = Toast.makeText(
            this, "Item ${deletedItem.label} dihapus", Toast.LENGTH_LONG
        )

        undoToast.show()

        // Tambahkan aksi untuk mengurungkan item
        undoToast.view?.setOnClickListener {
            // Kembalikan item yang dihapus ke dalam dataset
            imageLabelList.add(position, deletedItem)
            adapter.notifyItemInserted(position)
            undoToast.cancel()
        }

        // Atur durasi Toast sesuai kebutuhan
        Handler(Looper.getMainLooper()).postDelayed({
            undoToast.cancel()
        }, 3500) // Sesuaikan durasi sesuai keinginan (dalam milidetik)
    }

    fun sortItemsByTimestamp() {
        imageLabelList.sortBy { it.timestamp }
        adapter.notifyDataSetChanged()
    }

    private fun checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_EXTERNAL_STORAGE_PERMISSION_CODE
            )
        } else {
            // Permission is already granted, proceed with your code
            // You can load images or perform other operations here
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            READ_EXTERNAL_STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, proceed with your code
                    // You can load images or perform other operations here
                } else {
                    // Permission denied
                    // Handle the case where the user denied the permission
                }
                return
            }
            // Handle other permissions if needed
            // ...
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}

