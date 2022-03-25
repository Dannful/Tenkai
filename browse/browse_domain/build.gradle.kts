apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(Paging.compose)

    "implementation"(project(Modules.core))
}