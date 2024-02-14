@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.denisardent.catalog"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation( project(":presentation"))
    implementation(project(":common"))
    implementation(project(":feature:product"))
    implementation(project(":data:domain"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation (libs.fragment)
    implementation(libs.material)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.runtime)
    implementation(libs.adapterdelegates.delegate)
    implementation(libs.adapterdelegates.viewbinding)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation (libs.circleindicator)
    implementation(libs.viewpager)

    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
}