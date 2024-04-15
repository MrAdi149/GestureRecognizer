import org.gradle.api.Project
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("de.undercouch.download") version "5.6.0"
}

android {
    namespace = "com.example.gesturerecognizer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.gesturerecognizer"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Navigation library
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.7")

    // CameraX core library
    implementation ("androidx.camera:camera-core:1.3.2")

    // CameraX Camera2 extensions
    implementation ("androidx.camera:camera-camera2:1.3.2")

    // CameraX Lifecycle library
    implementation ("androidx.camera:camera-lifecycle:1.3.2")

    // CameraX View class
    implementation ("androidx.camera:camera-view:1.3.2")

    // WindowManager
    implementation ("androidx.window:window:1.2.0")

    // Unit testing
    testImplementation ("junit:junit:4.13.2")


    // Instrumented testing
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    // MediaPipe Library
//    implementation ("com.google.mediapipe:mediapipe-android:0.8.5")
    implementation ("com.google.mediapipe:tasks-vision:0.20230731")
}

project.ext["ASSET_DIR"] = "$projectDir/src/main/assets/"
apply(from = "download_tasks.gradle")