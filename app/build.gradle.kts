import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinCompose)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.aroy.ebookstore"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.aroy.ebookstore"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8) // or JVM_1_8 depending on your JDK
        }
    }
    buildFeatures {
        compose = true
    }
    buildFeatures {
        dataBinding = true
    }
}
detekt {
    toolVersion = libs.versions.detekt.get()
    config.setFrom("$rootDir/config/detekt/detekt.yml")
    buildUponDefaultConfig = true
    allRules = false
    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        reports {
            html.required.set(true)
            html.outputLocation.set(layout.buildDirectory.file("detekt-reports/detekt.html"))
            xml.required.set(false)   // disable XML
            md.required.set(true)     // enable Markdown
        }
    }
}

dependencies {
    // eBookStore Core Module

    // Core + Lifecycle + Fragment
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.kotlin.fragment)
    implementation(libs.compose.fragment)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.databinding.runtime)


    // Activity Compose
    implementation(libs.activity.compose)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.preview)
    debugImplementation(libs.compose.tooling)
    implementation(libs.compose.navigation)
    implementation(libs.compose.icons.core)
    implementation(libs.compose.icons.extended)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // This is the one that provides hiltViewModel()
    implementation(libs.hilt.navigation.compose)

    // Ktor + Serialization
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.negotiation)
    implementation(libs.ktor.serialization.json)
    implementation(libs.serialization.json)
    implementation(libs.ktor.serialization.gson)

    // Google Gson
    implementation(libs.google.gson)


    // Coroutines
    implementation(libs.coroutines.android)

    // Coil
    implementation(libs.coil.compose)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso.core)
}