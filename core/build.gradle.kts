plugins {
    id("com.apollographql.apollo").version(Apollo.version)
}

apply {
    from("$rootDir/base-module.gradle")
}

apollo {
    generateKotlinModels.set(true)
}

dependencies {
    "implementation"(DataStore.dataStore)
    "implementation"(DataStore.preferences)

    "implementation"(Paging.compose)

    "implementation"(Apollo.runtime)
}