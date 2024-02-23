plugins {
    alias(libs.plugins.com.android.application)
    kotlin("android")
    alias(libs.plugins.hilt)
    alias(libs.plugins.gms)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint.gradle)
    alias(libs.plugins.dokka)
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
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("debug")
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
    kotlinOptions {
        jvmTarget = "17"
    }
    configurations {
        api {
            // org.jetbrains:annotations clashes with com.intellij:annotations
            exclude(group = "org.jetbrains", module = "annotations")

            // kotlinx.collections.immutable.toImmutableList clashes with okhttp.internal.toImmutableList
            exclude(group = "com.squareup.okhttp3", module = "okhttp")
        }
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.androidx.palette)

    /**
     * Jetpack Compose.
     */
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material)
    implementation(libs.material3)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.accompanist.drawablepainter)
    debugImplementation(libs.ui.tooling)

    /**
     * Images: Coil.
     * @see [https://coil-kt.github.io/coil/](Compose Destinations)
     */
    implementation(libs.coil.compose)

    /**
     * Navigation: Compose Destinations.
     * @see [https://composedestinations.rafaelcosta.xyz/](Compose Destinations)
     */
    implementation(libs.compose.destinations.core)
    ksp(libs.compose.destinations.ksp)

    /**
     * Network: Retrofit.
     * @see [https://github.com/square/retrofit](Retrofit)
     */
    implementation(libs.retrofit)

    /**
     * Dependency Injection: Hilt.
     * @see [https://developer.android.com/training/dependency-injection/hilt-android](Hilt)
     */
    implementation(libs.hilt.android)
    implementation(libs.hilt.android.testing)
    implementation(libs.hilt.ext.compiler)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    /**
     * Date & Time: kotlinx-datetime.
     * @see [https://github.com/Kotlin/kotlinx-datetime](kotlinx-datetime)
     */
    implementation(libs.kotlinx.datetime)

    /**
     * Immutable persistent collections for Kotlin.
     * @see [https://github.com/Kotlin/kotlinx-datetime](kotlinx-datetime)
     */
    implementation(libs.kotlinx.collections.immutable)

    /**
     * Express the open source software notices of libraries used in their apps.
     * @see [https://developers.google.com/android/guides/opensource](oss-licenses)
     */
    implementation(libs.play.services.oss.licenses)

    /**
     * Test: JUnit.
     * @see [https://junit.org](JUnit)
     */
    testImplementation(libs.junit)

    /**
     * Compose Lint: compose-lints by Slack
     * @see [slackhq.github.io/compose-lints](compose-lints)
     */
    lintChecks(libs.compose.lint.checks)

    /**
     * Documentation: Dokka for Android
     * @see [https://github.com/Kotlin/dokka](dokka)
     */
    dokkaPlugin(libs.dokka.android)
}

/**
 * Kotlin Lint: ktlint by Pinterest
 * @see [https://github.com/pinterest/ktlint](ktlint)
 */
configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
    version.set(libs.versions.ktlint.get())
    debug.set(true)
    verbose.set(true)
    android.set(true)
    ignoreFailures.set(true)
    enableExperimentalRules.set(true)
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}
