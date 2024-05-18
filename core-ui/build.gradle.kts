apply(from = "$rootDir/compose-module.gradle")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "${AppSettings.APP_PACKAGE}.core_ui"
}

dependencies {
    implementation(projects.core)

    implementation(libs.coil.compose)

    implementation(libs.androidx.paging.compose)

    implementation(libs.kotlinx.serialization.json)
}