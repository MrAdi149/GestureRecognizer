***Gesture Recognizer App***

This Android application demonstrates gesture recognition using the Android GestureDetector and MediaPipe Hand Landmarker library.

***Features***

Gesture Detection: Recognizes various gestures including taps, long presses, and flings.
Finger Count Display: Displays the number of fingers tapped on the screen.
Hand Movement Analysis: Detects straight lines beyond joints in hand movements.
Integration with MediaPipe: Utilizes the MediaPipe Hand Landmarker library for hand tracking and landmark detection.

***Installation***

Clone the repository to your local machine:
git clone <repository-url>
Open the project in Android Studio.
Build and run the application on an Android device or emulator.

***Usage***

Tap Gesture: Tap on the screen with one or multiple fingers to see the finger count displayed in the TextView.
Long Press Gesture: Perform a long press gesture to detect long presses, and the corresponding message will be shown.
Fling Gesture: Swipe your finger(s) across the screen to trigger fling gestures, and the appropriate message will be displayed.
Hand Movement Analysis: The app analyzes hand movements in real-time, detecting straight lines beyond joints.

***Dependencies***

AndroidX Libraries: The project utilizes AndroidX libraries for Android development.
MediaPipe Hand Landmarker Library: Integration with the MediaPipe Hand Landmarker library for hand tracking and landmark detection.

***Contributing***

Contributions are welcome! If you have any ideas for improvements or bug fixes, feel free to submit a pull request. Please follow the existing code style and guidelines.

***License***

This project is licensed under the MIT License.

***Acknowledgments***

MediaPipe Team: Thanks to the MediaPipe team for providing the Hand Landmarker library, enabling advanced hand tracking and gesture recognition in Android applications.

Android Developer Documentation: Referenced Android Developer documentation for understanding GestureDetector and MotionEvent handling.
