plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.healthsakhi"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.healthsakhi"
        minSdk = 21
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
}

dependencies {
    // Core AndroidX libraries
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.annotation)
    implementation(libs.activity)

    // ✅ ROOM DATABASE (fixed)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.common.jvm)
    annotationProcessor(libs.androidx.room.compiler)


    // Material Design
    implementation(libs.material)

    // Navigation Drawer
    implementation(libs.androidx.drawerlayout)

    // UI Legacy support (optional)
    implementation(libs.legacy.support.v13)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.cardview)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
