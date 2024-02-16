plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.chrrissoft.room"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.chrrissoft.room"
        minSdk = 23
        targetSdk = 34
        versionCode = 6
        versionName = "1.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.credentials:credentials:1.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("com.google.dagger:hilt-android:2.48.1") // hilt-android
    kapt("com.google.dagger:hilt-android-compiler:2.48.1") // hilt-android-compiler

    implementation("com.jakewharton.threetenabp:threetenabp:1.4.6") // threetenabp

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.30.1") // systemuicontroller

    implementation("androidx.navigation:navigation-compose:2.7.7") // navigation-compose
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0") // hilt-navigation-compose

    implementation("androidx.compose.material:material-icons-extended:1.6.1") // material-icons

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0") // serialization-json

    implementation("com.airbnb.android:lottie-compose:6.2.0") // lottie

    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1") // room-ktx
    implementation("androidx.room:room-runtime:2.6.1") // room-runtime
    annotationProcessor("androidx.room:room-compiler:2.6.1") // room:room-compiler

    implementation("androidx.work:work-runtime-ktx:2.9.0") // work-runtime-ktx
    implementation("androidx.hilt:hilt-work:1.1.0") // hilt-work

    implementation("com.jakewharton.threetenabp:threetenabp:1.4.6") // threetenabp
}

kapt {
    correctErrorTypes = true
}