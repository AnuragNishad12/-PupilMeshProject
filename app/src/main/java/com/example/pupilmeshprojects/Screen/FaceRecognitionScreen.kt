package com.example.pupilmeshprojects.Screen


import android.Manifest
import android.content.pm.PackageManager
import android.graphics.RectF
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun FaceRecognitionScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Camera permission state
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }

    // Face detection state
    var faceDetected by remember { mutableStateOf(false) }
    var faceWithinRectangle by remember { mutableStateOf(false) }
    var currentFaceRect by remember { mutableStateOf<RectF?>(null) }

    // Define reference rectangle - centered rectangle that occupies 60% of width and 70% of height
    val referenceRect = remember {
        Rect(0.2f, 0.15f, 0.8f, 0.85f) // Left, Top, Right, Bottom as fractions of screen
    }

    // Camera permission launcher
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCameraPermission = granted
        }
    )

    // Request camera permission if needed
    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // Camera executor
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    // Cleanup resources when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            cameraExecutor.shutdown()
        }
    }

    // Create ML Kit face detector
    val faceDetector = remember {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_NONE)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_NONE)
            .setMinFaceSize(0.15f)
            .enableTracking()
            .build()

        FaceDetection.getClient(options)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (hasCameraPermission) {
            CameraPreview(
                modifier = Modifier.fillMaxSize(),
                faceDetector = faceDetector,
                cameraExecutor = cameraExecutor,
                onFaceDetected = { detected, withinRect, rect ->
                    faceDetected = detected
                    faceWithinRectangle = withinRect
                    currentFaceRect = rect
                },
                referenceRect = referenceRect
            )

            // Draw reference rectangle
            val rectangleColor = if (faceWithinRectangle) Color.Green else Color.Red
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Draw reference rectangle
                drawRect(
                    color = rectangleColor,
                    topLeft = Offset(
                        size.width * referenceRect.left,
                        size.height * referenceRect.top
                    ),
                    size = Size(
                        size.width * (referenceRect.right - referenceRect.left),
                        size.height * (referenceRect.bottom - referenceRect.top)
                    ),
                    style = Stroke(width = 4.dp.toPx())
                )

                // Draw face detection rectangle if a face is detected
                currentFaceRect?.let { faceRect ->
                    drawRect(
                        color = Color.Yellow,
                        topLeft = Offset(
                            faceRect.left * size.width,
                            faceRect.top * size.height
                        ),
                        size = Size(
                            (faceRect.right - faceRect.left) * size.width,
                            (faceRect.bottom - faceRect.top) * size.height
                        ),
                        style = Stroke(width = 2.dp.toPx())
                    )
                }
            }

            // Status text
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.6f))
                    .padding(8.dp)
            ) {
                Text(
                    text = if (faceDetected) {
                        if (faceWithinRectangle) "Face Aligned" else "Align Face with Rectangle"
                    } else {
                        "No Face Detected"
                    },
                    color = if (faceWithinRectangle) Color.Green else Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        } else {
            // Show message if no camera permission
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Camera permission is required for face recognition")
            }
        }
    }
}

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    faceDetector: FaceDetector,
    cameraExecutor: ExecutorService,
    onFaceDetected: (Boolean, Boolean, RectF?) -> Unit,
    referenceRect: Rect
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Preview view for camera
    val previewView = remember { PreviewView(context) }

    AndroidView(
        factory = { previewView },
        modifier = modifier
    ) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // Set up preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

            // Set up image analysis for face detection
            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor) { imageProxy ->
                        processImageForFaceDetection(imageProxy, faceDetector, onFaceDetected, referenceRect)
                    }
                }

            // Image capture (optional, for future enhancements)
            val imageCapture = ImageCapture.Builder().build()

            // Select front camera
            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .build()

            try {
                // Unbind existing uses
                cameraProvider.unbindAll()

                // Bind camera to lifecycle
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture,
                    imageAnalysis
                )
            } catch (e: Exception) {
                Log.e("FaceRecognitionScreen", "Camera binding failed", e)
            }
        }, ContextCompat.getMainExecutor(context))
    }
}

@OptIn(ExperimentalGetImage::class)
private fun processImageForFaceDetection(
    imageProxy: ImageProxy,
    faceDetector: FaceDetector,
    onFaceDetected: (Boolean, Boolean, RectF?) -> Unit,
    referenceRect: Rect
) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(
            mediaImage,
            imageProxy.imageInfo.rotationDegrees
        )

        faceDetector.process(image)
            .addOnSuccessListener { faces ->
                // Check if any faces were detected
                if (faces.isEmpty()) {
                    onFaceDetected(false, false, null)
                } else {
                    // Use the first detected face for simplicity
                    val face = faces[0]
                    val boundingBox = face.boundingBox

                    // Convert to normalized coordinates (0-1)
                    val width = image.width.toFloat()
                    val height = image.height.toFloat()

                    // Account for rotation and mirroring for front camera
                    val normalizedRect = RectF(
                        boundingBox.left / width,
                        boundingBox.top / height,
                        boundingBox.right / width,
                        boundingBox.bottom / height
                    )

                    // Mirror the coordinates horizontally for front camera
                    val mirroredRect = RectF(
                        1f - normalizedRect.right,
                        normalizedRect.top,
                        1f - normalizedRect.left,
                        normalizedRect.bottom
                    )

                    // Check if face is within reference rectangle
                    val faceWithinRectangle = mirroredRect.left >= referenceRect.left &&
                            mirroredRect.top >= referenceRect.top &&
                            mirroredRect.right <= referenceRect.right &&
                            mirroredRect.bottom <= referenceRect.bottom

                    onFaceDetected(true, faceWithinRectangle, mirroredRect)
                }
            }
            .addOnFailureListener { e ->
                Log.e("FaceRecognitionScreen", "Face detection failed", e)
                onFaceDetected(false, false, null)
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    } else {
        imageProxy.close()
    }
}

