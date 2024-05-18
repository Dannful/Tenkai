apply(from = "$rootDir/compose-module.gradle")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
}

android {
    namespace = "${AppSettings.APP_PACKAGE}.media_search_presentation"
}

dependencies {
    implementation(projects.mediaSearch.mediaSearchDomain)

    implementation(projects.coreUi)
    implementation(projects.core)

    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging)

    implementation(libs.coil.compose)

    implementation(libs.androidx.compose.material)
}