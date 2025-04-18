package com.example.pupilmeshprojects


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facedetector.FaceDetector

class FaceRecognitionViewModel(context: android.content.Context) {
    private var previewWidth by mutableStateOf(0)
    private var previewHeight by mutableStateOf(0)
    var isFaceInside by mutableStateOf(false)
        private set

    private val faceDetector: FaceDetector by lazy {
        FaceDetector.createFromOptions(
            context,
            FaceDetector.FaceDetectorOptions.builder()
                .setRunningMode(RunningMode.LIVE_STREAM)
                .setMinDetectionConfidence(0.5f)
                .build()
        )
    }

    fun updatePreviewSize(width: Int, height: Int) {
        previewWidth = width
        previewHeight = height
    }
}