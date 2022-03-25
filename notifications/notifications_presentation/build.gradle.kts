apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.notificationsDomain))
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.core))

    "implementation"(Paging.compose)
}