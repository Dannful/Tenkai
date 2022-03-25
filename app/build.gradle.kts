plugins {
    id("com.android.application")
    kotlin("android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    android.namespace = ProjectConfig.appId
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.appId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "${ProjectConfig.appId}.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeCompilerVersion
    }
    packagingOptions {
        exclude("META-INF/AL2.0")
        exclude("META-INF/LGPL2.1")
        exclude("**/attach_hotspot_windows.dll")
        exclude("META-INF/licenses/ASM")
    }
}

dependencies {
    implementation(Compose.compiler)
    implementation(Compose.ui)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.hiltNavigationCompose)
    implementation(Compose.material)
    implementation(Compose.runtime)
    implementation(Compose.navigation)
    implementation(Compose.viewModelCompose)
    implementation(Compose.activityCompose)

    implementation(DaggerHilt.hiltAndroid)
    kapt(DaggerHilt.hiltCompiler)

    implementation(DataStore.dataStore)
    implementation(DataStore.preferences)

    implementation(Apollo.runtime)

    implementation(Work.workRuntime)
    implementation(HiltWork.hiltWork)
    kapt(HiltWork.hiltWorkCompiler)

    implementation(project(Modules.core))
    implementation(project(Modules.coreUi))
    implementation(project(Modules.homePresentation))
    implementation(project(Modules.homeDomain))
    implementation(project(Modules.homeData))
    implementation(project(Modules.browsePresentation))
    implementation(project(Modules.browseDomain))
    implementation(project(Modules.browseData))
    implementation(project(Modules.profilePresentation))
    implementation(project(Modules.profileDomain))
    implementation(project(Modules.profileData))
    implementation(project(Modules.notificationsPresentation))
    implementation(project(Modules.notificationsDomain))
    implementation(project(Modules.notificationsData))
    implementation(project(Modules.onboardingPresentation))
    implementation(project(Modules.onboardingDomain))
    implementation(project(Modules.onboardingData))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.appCompat)

    implementation(Coil.compose)

    implementation(Google.material)

    kapt(Room.roomCompiler)
    implementation(Room.roomKtx)
    implementation(Room.roomRuntime)

    testImplementation(Testing.junit4)
    testImplementation(Testing.junitAndroidExt)
    testImplementation(Testing.truth)
    testImplementation(Testing.coroutines)
    testImplementation(Testing.turbine)
    testImplementation(Testing.composeUiTest)
    testImplementation(Testing.mockk)

    androidTestImplementation(Testing.junit4)
    androidTestImplementation(Testing.junitAndroidExt)
    androidTestImplementation(Testing.truth)
    androidTestImplementation(Testing.coroutines)
    androidTestImplementation(Testing.turbine)
    androidTestImplementation(Testing.composeUiTest)
    androidTestImplementation(Testing.mockkAndroid)
    androidTestImplementation(Testing.hiltTesting)
    kaptAndroidTest(DaggerHilt.hiltCompiler)
    androidTestImplementation(Testing.testRunner)
}
