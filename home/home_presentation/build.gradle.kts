apply(from = "$rootDir/compose-module.gradle")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
}

android {
    namespace = "${AppSettings.APP_PACKAGE}.home_presentation"
}

dependencies {
    implementation(projects.coreUi)
    implementation(projects.core)

    implementation(projects.home.homeDomain)

    implementation(libs.androidx.paging.compose)

    implementation(libs.androidx.compose.material)

    implementation(libs.coil.compose)
}