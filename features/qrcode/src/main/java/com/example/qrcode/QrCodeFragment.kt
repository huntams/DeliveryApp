package com.example.qrcode

import android.os.Bundle
import android.view.View
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.common.PrefsStorage
import com.example.qrcode.databinding.FragmentCameraBinding
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlin.random.Random


@AndroidEntryPoint
class QrCodeFragment : Fragment(R.layout.fragment_camera) {

    @Inject
    lateinit var prefsStorage: PrefsStorage

    private val binding by viewBinding(FragmentCameraBinding::bind)

    private val cameraSelector: CameraSelector by lazy {
        CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
    }
    private lateinit var cameraPreview: Preview
    private lateinit var processCameraProvider: ProcessCameraProvider
    private lateinit var imageAnalysis: ImageAnalysis
    private val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> by lazy {
        ProcessCameraProvider.getInstance(
            requireContext()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cameraProviderFuture.addListener(
            {
                processCameraProvider = cameraProviderFuture.get()
                bindCameraPreview()
                bindAnalyzer()
            }, ContextCompat.getMainExecutor(requireContext())
        )
    }

    private fun bindAnalyzer() {
        val barcodeScanner = BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE).build()
        )
        imageAnalysis =
            ImageAnalysis.Builder().setTargetRotation(binding.viewFinder.display.rotation).build()
        val cameraExecutor = Executors.newSingleThreadExecutor()

        imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
            processImageProxy(barcodeScanner, imageProxy)
        }
        processCameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis)
    }

    @OptIn(ExperimentalGetImage::class)
    private fun processImageProxy(barcodeScanner: BarcodeScanner, imageProxy: ImageProxy) {

        val inputImage =
            InputImage.fromMediaImage(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees)
        barcodeScanner.process(inputImage).addOnCompleteListener {
            imageProxy.close()
        }.addOnSuccessListener { barcodes ->
            if (barcodes.isNotEmpty()) {
                prefsStorage.coins += Random.nextInt(300, 1000)
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }.addOnFailureListener {
            it.printStackTrace()
        }
    }

    private fun bindCameraPreview() {
        cameraPreview =
            Preview.Builder().setTargetRotation(binding.viewFinder.display.rotation).build()
        cameraPreview.setSurfaceProvider(binding.viewFinder.surfaceProvider)
        processCameraProvider.bindToLifecycle(this, cameraSelector, cameraPreview)
    }
}