plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")

}

android {
    namespace = "com.example.pupilmeshprojects"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.pupilmeshprojects"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)


    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)


    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)


//    implementation(libs.datastore.preferences)
    implementation ("androidx.datastore:datastore-preferences:1.0.0-alpha01")

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")
//    implementation ("com.google.mediapipe:tasks-vision:0.10.0")
//    implementation ("androidx.camera:camera-core:1.3.0")
//    implementation ("androidx.camera:camera-camera2:1.3.0")
//    implementation ("androidx.camera:camera-lifecycle:1.3.0")
//    implementation ("androidx.camera:camera-view:1.3.0")
    // CameraX
    implementation ("androidx.camera:camera-camera2:1.3.0")
    implementation ("androidx.camera:camera-lifecycle:1.3.0")
    implementation ("androidx.camera:camera-view:1.3.0")
    implementation ("com.google.mlkit:face-detection:16.1.5")

// MediaPipe
    implementation ("com.google.mediapipe:tasks-vision:0.10.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
 implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
 implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
 implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
 implementation ("androidx.navigation:navigation-compose:2.7.7")
 implementation ("io.coil-kt:coil-compose:2.4.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

// Gson (for JSON parsing)
    implementation ("com.google.code.gson:gson:2.10.1")

}