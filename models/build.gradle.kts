plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.apollo.graphql)
}

android {
    compileSdk = AppSettings.SDK
    namespace = "${AppSettings.APP_PACKAGE}.models"
    defaultConfig {
        minSdk = AppSettings.MIN_SDK
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

apollo {
    service("service") {
        packageName.set("com.github.dannful.models")
        introspection {
            endpointUrl.set("https://graphql.anilist.co")
            schemaFile.set(file("src/main/graphql/schema.graphqls"))
        }
    }
}

dependencies {
    implementation(libs.apollo.runtime)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}