apply(from = "$rootDir/base-module.gradle")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
    alias(libs.plugins.com.google.dev.tools.ksp)
}

android {
    namespace = "${AppSettings.APP_PACKAGE}.home_data"
}

dependencies {
    implementation(projects.models)

    implementation(projects.home.homeDomain)

    implementation(projects.core)

    implementation(libs.apollo.runtime)

    implementation(libs.androidx.paging)

    implementation(libs.androidx.datastore)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    implementation(libs.kotlinx.serialization.json)
}