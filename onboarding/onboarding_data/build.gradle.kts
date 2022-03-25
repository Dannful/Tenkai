apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.onboardingDomain))

    "implementation"(Coroutines.core)
    "implementation"(Apollo.runtime)
    "implementation"(Apollo.coroutinesSupport)
}