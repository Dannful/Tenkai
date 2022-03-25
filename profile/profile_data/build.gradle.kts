apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.profileDomain))
    "implementation"(project(Modules.core))

    "implementation"(Paging.compose)

    "implementation"(Apollo.runtime)
    "implementation"(Apollo.coroutinesSupport)
}