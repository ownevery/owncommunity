plugins {
    alias(libs.plugins.com.android.application)
    kotlin("android")
    alias(libs.plugins.hilt)
    alias(libs.plugins.gms)
    alias(libs.plugins.ksp)
}
android {
    namespace = libs.versions.build.namespace.get()
    compileSdk = libs.versions.build.sdk.compile.get().toIntOrNull()

    defaultConfig {
        applicationId = libs.versions.build.namespace.get()
        minSdk = libs.versions.build.sdk.min.get().toIntOrNull()
        targetSdk = libs.versions.build.sdk.target.get().toIntOrNull()
        versionCode = libs.versions.build.code.get().toIntOrNull()
        versionName = libs.versions.build.name.get()

        testInstrumentationRunner = libs.versions.build.testInstrumentationRunner.get()
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    configurations {
        api {
            exclude(group = "org.jetbrains", module = "annotations")
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material)
    implementation(libs.material3)

    implementation(libs.compose.destinations.core)
    ksp(libs.compose.destinations.ksp)

    implementation(libs.hilt.android)
    implementation(libs.hilt.android.testing)
    implementation(libs.hilt.ext.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    implementation(libs.kotlinx.datetime)
    implementation(libs.retrofit)
    implementation(libs.coil.compose)
    implementation(libs.androidx.palette)
    implementation(libs.accompanist.drawablepainter)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}
