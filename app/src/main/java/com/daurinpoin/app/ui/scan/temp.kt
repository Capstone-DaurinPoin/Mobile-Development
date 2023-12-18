package com.daurinpoin.app.ui.scan

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.daurinpoin.app.databinding.ActivityScanBinding
import com.daurinpoin.app.ui.scan.helper.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat
import java.util.concurrent.Executors

/*
class temp {

    private lateinit var imageClassifierHelper: ImageClassifierHelper
    private lateinit var binding: ActivityScanBinding
    private val handler = Handler(Looper.getMainLooper())

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            val message = if (isGranted) "Camera permission granted" else "Camera permission rejected"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            if (isGranted) {
                initializeGpuDelegate()
                startCamera()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
    }

    private fun initializeGpuDelegate() {
        handler.post {
            try {
                imageClassifierHelper = ImageClassifierHelper(
                    context = this,
                    imageClassifierListener = object : ImageClassifierHelper.ClassifierListener {
                        override fun onError(error: String) {
                            runOnUiThread {
                                Toast.makeText(this@ScanActivity, error, Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onResults(results: List<Classifications>?, inferenceTime: Long) {
                            runOnUiThread {
                                results?.let { it ->
                                    if (it.isNotEmpty() && it[0].categories.isNotEmpty()) {
                                        val sortedCategories =
                                            it[0].categories.sortedByDescending { it?.score }
                                        val displayResult =
                                            sortedCategories.joinToString("\n") {
                                                "${it.label} " + NumberFormat.getPercentInstance()
                                                    .format(it.score).trim()
                                            }
                                        binding.tvResult.text = displayResult
                                    }
                                }
                            }
                        }
                    }
                )
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error initializing GPUDelegate: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            val imageAnalyzer =
                ImageAnalysis.Builder()
                    .setTargetAspectRatio(AspectRatio.RATIO_4_3)
                    .setTargetRotation(binding.viewFinder.display.rotation)
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                    .build()
                    .also {
                        it.setAnalyzer(Executors.newSingleThreadExecutor()) { image ->
                            imageClassifierHelper.classify(image)
                        }
                    }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageAnalyzer
                )
            } catch (exc: Exception) {
                Toast.makeText(this, "Failed to start camera.", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    override fun onDestroy() {
        imageClassifierHelper.clearImageClassifier()
        super.onDestroy()
    }
}*/
