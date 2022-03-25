apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.homeDomain))

    "implementation"(Paging.compose)

    "implementation"(Coil.compose)

    "implementation"(ViewPager.pager)
    "implementation"(ViewPager.indicators)
}