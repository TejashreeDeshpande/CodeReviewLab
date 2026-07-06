plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.tejashree.codereviewlab"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.tejashree.codereviewlab"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui)

    implementation(libs.kotlinx.collections.immutable)

    implementation("androidx.compose.material:material-icons-extended")
    // Koin BOM to manage versions consistently
    implementation(platform("io.insert-koin:koin-bom:4.1.0"))
    // Core Koin Android features
    implementation("io.insert-koin:koin-android")
    // Optional: If you use Jetpack Compose
    implementation("io.insert-koin:koin-androidx-compose")

    // Navigation 3 artifacts
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation3.ui)
    implementation("androidx.lifecycle:lifecycle-viewmodel-navigation3:2.11.0")

    // Serialization engine for type-safe route parsing
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}