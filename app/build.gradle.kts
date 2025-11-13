plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.souqcustomer"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.souqcustomer"
        minSdk = 26
        targetSdk = 36
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
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.3")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.3")

    implementation("me.relex:circleindicator:2.1.6")

    implementation("com.airbnb.android:lottie:6.0.0")

        // Retrofit core library
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")

        // Converter library for JSON serialization/deserialization (e.g., Gson)
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")// Use the same version as Retrofit
    implementation ("com.google.code.gson:gson:2.10.1")// Use the latest stable version of Gson

    implementation ("com.github.bumptech.glide:glide:4.16.0")



}