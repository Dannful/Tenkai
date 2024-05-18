pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "Tenkai"
include(":app")
include(":core-ui")
include(":onboarding")
include(":onboarding:onboarding_data")
include(":core")
include(":onboarding:onboarding_presentation")
include(":onboarding:onboarding_domain")
include(":home")
include(":home:home_presentation")
include(":models")
include(":home:home_data")
include(":home:home_domain")
include(":media_search")
include(":media_search:media_search_data")
include(":media_search:media_search_domain")
include(":media_search:media_search_presentation")
