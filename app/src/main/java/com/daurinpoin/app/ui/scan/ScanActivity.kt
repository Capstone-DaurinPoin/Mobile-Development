package com.daurinpoin.app.ui.scan

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.os.Environment
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.daurinpoin.app.MainActivity
import com.daurinpoin.app.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ScanActivity : AppCompatActivity() {

    private lateinit var surfaceView: SurfaceView
    private lateinit var captureButton: Button
    private var camera: Camera? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_scan)

        surfaceView = findViewById(R.id.surfaceView)
        captureButton = findViewById(R.id.captureButton)

        if (allPermissionsGranted()) {
            setupCamera()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }

        captureButton.setOnClickListener {
            takePicture()
        }
    }

    private fun setupCamera() {
        // Inisialisasi kamera
        camera = Camera.open()

        // Tambahkan SurfaceHolder.Callback untuk surfaceView
        surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    camera?.setPreviewDisplay(holder)
                    camera?.startPreview()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceChanged(
                holder: SurfaceHolder, format: Int, width: Int, height: Int
            ) {
                val parameters = camera?.parameters
                val supportedPreviewSizes = parameters?.supportedPreviewSizes
                val optimalSize = getOptimalPreviewSize(supportedPreviewSizes, width, height)

                parameters?.setPreviewSize(
                    optimalSize?.width ?: width, optimalSize?.height ?: height
                )
                camera?.parameters = parameters
                camera?.startPreview()
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                // Stop preview saat surface dihancurkan
                camera?.stopPreview()
                camera?.release()
            }
        })
    }

    private fun getOptimalPreviewSize(sizes: List<Camera.Size>?, w: Int, h: Int): Camera.Size? {
        val ASPECT_TOLERANCE = 0.1
        val targetRatio = w.toDouble() / h

        if (sizes == null) return null

        var optimalSize: Camera.Size? = null
        var minDiff = Double.MAX_VALUE

        for (size in sizes) {
            val ratio = size.width.toDouble() / size.height
            val diff = Math.abs(ratio - targetRatio)
            if (diff < minDiff) {
                optimalSize = size
                minDiff = diff
            }
        }

        return optimalSize
    }

    private fun takePicture() {
        // Ambil gambar dan simpan ke file
        camera?.takePicture(null, null, Camera.PictureCallback { data, _ ->
            val pictureFile = getOutputMediaFile()
            try {
                val fos = FileOutputStream(pictureFile)
                fos.write(data)
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        })
    }

    private fun getOutputMediaFile(): File {
        // Buat direktori penyimpanan gambar
        val mediaStorageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "CameraApp"
        )

        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdir()
        }

        // Buat nama file dengan timestamp
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return File("${mediaStorageDir.path}${File.separator}IMG_$timeStamp.jpg")
    }

    private fun allPermissionsGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            this, Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onBackPressed() {
        val resultIntent = Intent()
        setResult(Activity.RESULT_OK, resultIntent)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        super.onBackPressed()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (allPermissionsGranted()) {
                setupCamera()
            } else {
                // Handle the case where the user denied some or all of the requested permissions.
                // You may want to show a message or ask again for the missing permissions.
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 123
    }
}
