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

rootProject.name = "Movie App"
include(":app")
include(":core")
include(":data")
include(":feature")
include(":core:common")
include(":core:design")
include(":core:testing")
include(":data:model")
include(":data:remote")
include(":data:local")
include(":data:repository")
include(":feature:onboarding")
include(":feature:home")
include(":feature:movie_details")
include(":feature:search")
