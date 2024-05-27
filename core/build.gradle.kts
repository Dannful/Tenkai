apply(from = "$rootDir/base-module.gradle")

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.com.google.dev.tools.ksp)
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "${AppSettings.APP_PACKAGE}.core"
    buildFeatures.buildConfig = true
    defaultConfig {
        buildConfigField("int", "CLIENT_ID", properties["CLIENT_ID"].toString())
        buildConfigField("String", "CLIENT_SECRET", "\"${properties["CLIENT_SECRET"].toString()}\"")
        buildConfigField(
            "String",
            "CLIENT_REDIRECT_URL",
            "\"${properties["CLIENT_REDIRECT_URL"].toString()}\""
        )
    }
}

dependencies {
    implementation(projects.models)

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.work.runtime)

    implementation(libs.apollo.runtime)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.lifecycle.livedata)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.paging.compose)
}