plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.jetbrains.kotlin.kapt)
}

android {
    namespace = "${AppSettings.APP_PACKAGE}.onboarding_presentation"
}

dependencies {
    implementation(projects.onboarding.onboardingDomain)
    implementation(projects.coreUi)
    implementation(projects.core)
}

apply(from = "$rootDir/compose-module.gradle")