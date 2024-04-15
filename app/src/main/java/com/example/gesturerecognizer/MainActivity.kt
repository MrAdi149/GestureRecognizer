package com.example.gesturerecognizer

import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.gesturerecognizer.databinding.ActivityMainBinding
import com.google.mediapipe.tasks.vision.handlandmarker.HandLandmarkerResult

class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()
    private lateinit var gestureDetector: GestureDetector
    private var fingerCount = 0 // Variable to keep track of finger count
    private lateinit var textView: TextView
    private lateinit var overlayView: OverlayView
    private var straightLinesBeyondJoints = 0 // Variable to count straight lines beyond joints


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        textView = findViewById(R.id.textView)
        var fingerCount = 0 // Variable to keep track of finger count

        // Initialize GestureDetector with a SimpleOnGestureListener
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                // This method is called when a down event is detected
                textView.text = "Down event detected"
                return true
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                // Increment finger count on each tap up event
                fingerCount++

                // Limit finger count up to 5
                fingerCount = fingerCount.coerceAtMost(5)

                // Update TextView with finger count
                textView.text = if (fingerCount == 1) {
                    "One finger"
                } else {
                    "$fingerCount fingers"
                }
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                // This method is called when a long press gesture is detected
                textView.text = "Long press detected"
            }

            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                // This method is called when a fling gesture is detected
                textView.text = "Fling gesture detected"
                return true
            }
        })

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        activityMainBinding.navigation.setupWithNavController(navController)
        activityMainBinding.navigation.setOnNavigationItemReselectedListener {
            // ignore the reselection
        }
    }

    // Assuming you have a reference to your OverlayView instance called overlayView


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // Get the action and pointer count
        val action = event?.actionMasked
        val pointerCount = event?.pointerCount ?: 0

        when (action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                // Increment finger count on down events
                fingerCount = pointerCount.coerceAtMost(5)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                // Decrement finger count on up events
                fingerCount = pointerCount.coerceAtMost(5)
            }
        }

        // Ensure finger count doesn't go below 0
        fingerCount = fingerCount.coerceAtLeast(0)

        // Update TextView with finger count
        textView.text = if (fingerCount == 1) {
            "3 fingers"
        } else {
            "$fingerCount fingers"
        }

        // Detect straight lines beyond joints and update the count
        when (action) {
            MotionEvent.ACTION_MOVE -> {
                // Iterate over all the pointers
                for (i in 0 until pointerCount) {
                    // Get the coordinates of the pointer
                    val x = event.getX(i)
                    val y = event.getY(i)

                    // Check if the pointer is beyond a joint (implement your logic here)
                    if (isBeyondJoint(x, y)) {
                        // Increment the count of straight lines beyond joints
                        straightLinesBeyondJoints++
                    }
                }
            }
        }

        return true
    }

    // Function to check if the pointer is beyond a joint
    private fun isBeyondJoint(x: Float, y: Float): Boolean {
        // Implement your logic to detect if the pointer is beyond a joint
        // For example, you can use the coordinates of the joints and check if the pointer lies beyond them
        // Return true if the pointer is beyond a joint, false otherwise
        return false // Placeholder logic, replace it with your implementation
    }



    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}