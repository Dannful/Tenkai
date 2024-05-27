apply(from = "$rootDir/base-module.gradle")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.jetbrains.kotlin.serialization)
    alias(libs.plugins.com.google.dev.tools.ksp)
}

android {
    namespace = "${AppSettings.APP_PACKAGE}.onboarding_data"
}

dependencies {
    implementation(projects.core)

    implementation(projects.models)

    implementation(projects.onboarding.onboardingDomain)

    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.content.negotiation)

    implementation(libs.androidx.datastore)

    implementation(libs.apollo.runtime)
}