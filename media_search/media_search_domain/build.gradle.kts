apply(from = "$rootDir/base-module.gradle")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.com.google.dev.tools.ksp)
}

android {
    namespace = "${AppSettings.APP_PACKAGE}.media_search_domain"
}

dependencies {
    implementation(projects.core)

    implementation(libs.androidx.paging)
}