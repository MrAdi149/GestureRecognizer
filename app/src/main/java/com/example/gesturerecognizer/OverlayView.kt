package com.example.gesturerecognizer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.example.gesturerecognizer.HandLandmarkerHelper.Companion.FINGER_BASE_INDEXES
import com.example.gesturerecognizer.HandLandmarkerHelper.Companion.FINGER_RAISED_THRESHOLD
import com.example.gesturerecognizer.HandLandmarkerHelper.Companion.FINGER_TIP_INDEXES
import com.example.gesturerecognizer.HandLandmarkerHelper.Companion.NUM_FINGERS
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarker
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sqrt

class OverlayView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    // Define the running mode variable
    private var runningMode: RunningMode = RunningMode.LIVE_STREAM
    private var imageWidth: Int = 1
    private var imageHeight: Int = 1
    private var scaleFactor: Float = 1f
    private val yOffset: Float = 10f // Adjust this value as needed


    private var results: HandLandmarkerResult? = null

    private var linePaint = Paint().apply {
        color = Color.RED // Set line color to green
        strokeWidth = LANDMARK_STROKE_WIDTH
        style = Paint.Style.STROKE
    }
    private var pointPaint = Paint().apply {
        color = Color.GREEN
        strokeWidth = LANDMARK_STROKE_WIDTH
        style = Paint.Style.FILL
    }
    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        results?.let { handLandmarkerResult ->
            if (runningMode == RunningMode.LIVE_STREAM) {
                // Camera running mode
                drawForGallery(canvas, handLandmarkerResult)
                return
            } else {
                // Gallery image view mode
                drawForCamera(canvas, handLandmarkerResult)
            }
        }
    }

    private fun drawForCamera(canvas: Canvas, handLandmarkerResult: HandLandmarkerResult) {
        for (landmark in handLandmarkerResult.landmarks()) {
            for (normalizedLandmark in landmark) {
                canvas.drawPoint(
                    normalizedLandmark.x() * imageWidth * scaleFactor,
                    normalizedLandmark.y() * imageHeight * scaleFactor,
                    pointPaint
                )
            }

            HandLandmarker.HAND_CONNECTIONS.forEach {
                canvas.drawLine(
                    landmark.get(it!!.start()).x() * imageWidth * scaleFactor,
                    landmark.get(it.start()).y() * imageHeight * scaleFactor,
                    landmark.get(it.end()).x() * imageWidth * scaleFactor,
                    landmark.get(it.end()).y() * imageHeight * scaleFactor,
                    linePaint
                )
            }
        }
    }

    private fun drawForGallery(canvas: Canvas, handLandmarkerResult: HandLandmarkerResult) {
        // Calculate the center of the screen
        val centerX = width / 2f
        val centerY = height / 2f

        // Calculate the width and height of the overlay
        val overlayWidth = imageWidth * scaleFactor
        val overlayHeight = imageHeight * scaleFactor

        for (landmark in handLandmarkerResult.landmarks()) {
            for (connection in HandLandmarker.HAND_CONNECTIONS) {
                val startPoint = landmark.get(connection?.start() ?: 0)
                val endPoint = landmark.get(connection?.end() ?: 0)

                // Calculate the scaled coordinates for the line based on the center of the screen
                val startX = centerX + (startPoint.x() - 0.5f) * overlayWidth
                val startY = centerY + (startPoint.y() - 0.5f) * overlayHeight
                val endX = centerX + (endPoint.x() - 0.5f) * overlayWidth
                val endY = centerY + (endPoint.y() - 0.5f) * overlayHeight

                // Draw lines
                canvas.drawLine(
                    startX,
                    startY,
                    endX,
                    endY,
                    linePaint
                )
            }

            for (normalizedLandmark in landmark) {
                // Calculate the scaled coordinates based on the center of the screen
                val scaledX = centerX + (normalizedLandmark.x() - 0.5f) * overlayWidth
                val scaledY = centerY + (normalizedLandmark.y() - 0.5f) * overlayHeight

                // Draw points
                canvas.drawPoint(
                    scaledX,
                    scaledY + yOffset, // Adjust Y coordinate by a fixed offset if needed
                    pointPaint
                )
            }
        }
    }

    fun setRunningMode(mode: RunningMode) {
        runningMode = mode
    }

    fun setImageDimensionsAndScaleFactor(width: Int, height: Int, factor: Float) {
        imageWidth = width
        imageHeight = height
        scaleFactor = factor
    }

    fun setResults(
        handLandmarkerResults: HandLandmarkerResult,
        imageHeight: Int,
        imageWidth: Int,
        runningMode: RunningMode = RunningMode.IMAGE
    ) {
        results = handLandmarkerResults

        this.imageHeight = imageHeight
        this.imageWidth = imageWidth

        scaleFactor = when (runningMode) {
            RunningMode.IMAGE, RunningMode.VIDEO ->
                min(width * 1f / imageWidth, height * 1f / imageHeight)
            RunningMode.LIVE_STREAM ->
                max(width * 1f / imageWidth, height * 1f / imageHeight)
        }
        invalidate()
    }

    fun clear() {
        results = null
        linePaint.reset()
        pointPaint.reset()
        invalidate()
        initPaints()
    }

    private fun initPaints() {
        linePaint.color =
            ContextCompat.getColor(context!!, R.color.mp_color_error)
        linePaint.strokeWidth = LANDMARK_STROKE_WIDTH
        linePaint.style = Paint.Style.STROKE

        pointPaint.color = Color.GREEN
        pointPaint.strokeWidth = LANDMARK_STROKE_WIDTH
        pointPaint.style = Paint.Style.FILL
    }

    companion object {
        private const val LANDMARK_STROKE_WIDTH = 8F
    }
}