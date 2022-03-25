apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(Paging.compose)

    "implementation"(Apollo.runtime)
    "implementation"(Apollo.coroutinesSupport)

    "implementation"(Room.roomRuntime)
    "implementation"(Room.roomPaging)
    "kapt"(Room.roomCompiler)
    "implementation"(Room.roomKtx)

    "implementation"(project(Modules.homeDomain))
    "implementation"(project(Modules.core))
}