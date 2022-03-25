apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.notificationsDomain))
    "implementation"(project(Modules.core))

    "implementation"(Apollo.runtime)
    "implementation"(Apollo.coroutinesSupport)

    "implementation"(Paging.compose)
}