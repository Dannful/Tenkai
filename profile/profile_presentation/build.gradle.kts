apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUi))
    "implementation"(project(Modules.profileDomain))

    "implementation"(Paging.compose)

    "implementation"(ViewPager.pager)
    "implementation"(ViewPager.indicators)

    "implementation"(Coil.compose)
}