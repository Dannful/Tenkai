apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(Coil.compose)

    "implementation"(SwipeRefresh.swipeRefresh)

    "implementation"(Paging.compose)

    "implementation"(project(Modules.core))

    "implementation"(Kotlin.reflect)
}