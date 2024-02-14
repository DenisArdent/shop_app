pluginManagement {
    repositories {
        google()
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

rootProject.name = "test case"
include(":app")
include(":data:network")
include(":data:domain")
include(":common")
include(":feature:catalog")
include(":presentation")
include(":feature:product")
include(":data:local")
include(":feature:authorization")
include(":feature:profile")
