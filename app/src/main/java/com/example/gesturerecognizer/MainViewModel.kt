package com.example.gesturerecognizer

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var _delegate: Int = HandLandmarkerHelper.DELEGATE_CPU
    private var _minHandDetectionConfidence: Float =
        HandLandmarkerHelper.DEFAULT_HAND_DETECTION_CONFIDENCE
    private var _minHandTrackingConfidence: Float = HandLandmarkerHelper
        .DEFAULT_HAND_TRACKING_CONFIDENCE
    private var _minHandPresenceConfidence: Float = HandLandmarkerHelper
        .DEFAULT_HAND_PRESENCE_CONFIDENCE
    val currentDelegate: Int get() = _delegate
    val currentMinHandDetectionConfidence: Float
        get() =
            _minHandDetectionConfidence
    val currentMinHandTrackingConfidence: Float
        get() =
            _minHandTrackingConfidence
    val currentMinHandPresenceConfidence: Float
        get() =
            _minHandPresenceConfidence

    fun setDelegate(delegate: Int) {
        _delegate = delegate
    }

    fun setMinHandDetectionConfidence(confidence: Float) {
        _minHandDetectionConfidence = confidence
    }

    fun setMinHandTrackingConfidence(confidence: Float) {
        _minHandTrackingConfidence = confidence
    }

    fun setMinHandPresenceConfidence(confidence: Float) {
        _minHandPresenceConfidence = confidence
    }
}