package com.daurinpoin.app.ui.scan

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.daurinpoin.app.R
import com.daurinpoin.app.ml.ModelLawasV1
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.label.Category
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ScanActivity : AppCompatActivity() {

    private lateinit var model: ModelLawasV1

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                        classifyImageAndDisplay(bitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.extras?.get("data")?.let { imageBitmap ->
                    // Save the captured image to the gallery
                    saveImageToGallery(imageBitmap as Bitmap)
                    // Classify and display the captured image
                    classifyImageAndDisplay(imageBitmap)
                }
            }
        }

    private lateinit var imageView: ImageView
    private lateinit var tvClassificationResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        val btnOpenGallery: Button = findViewById(R.id.btnOpenGallery)
        val btnOpenCamera: Button = findViewById(R.id.btnOpenCamera)
        imageView = findViewById(R.id.imageView)
        tvClassificationResult = findViewById(R.id.tvClassificationResult)

        // Initialize the model
        model = ModelLawasV1.newInstance(this)

        // Set onClickListener for the gallery button
        btnOpenGallery.setOnClickListener {
            openGallery()
        }

        // Set onClickListener for the camera button
        btnOpenCamera.setOnClickListener {
            openCamera()
        }
    }

    private fun classifyImageAndDisplay(bitmap: Bitmap) {
        // Show the captured image
        imageView.setImageBitmap(bitmap)
        imageView.visibility = ImageView.VISIBLE

        // Classify the image
        val image = TensorImage.fromBitmap(bitmap)
        val outputs = model.process(image)
        val probability: List<Category> = outputs.probabilityAsCategoryList

        // Sort the categories by score in descending order
        val sortedProbability = probability.sortedByDescending { it.score }

        // Process the classification results
        val resultText = buildString {
            for (i in 0 until minOf(2, sortedProbability.size)) {
                val category = sortedProbability[i]
                append("${category.label}: ${category.score}\n")
            }
        }

        // Show the classification results
        tvClassificationResult.text = resultText
        tvClassificationResult.visibility = TextView.VISIBLE
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    private fun saveImageToGallery(bitmap: Bitmap) {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "Image_${getTimestamp()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        }

        val resolver = contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            try {
                val outputStream: OutputStream? = resolver.openOutputStream(uri)
                outputStream?.use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun getTimestamp(): String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        return sdf.format(Date())
    }

    override fun onDestroy() {
        super.onDestroy()
        // Release model resources when the activity is destroyed
        model.close()
    }
}



