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
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "MobileCodeSpace"

include(":app")

// Core Modules
include(":core:ui")
include(":core:data")
include(":core:di")
include(":core:domain")
include(":core:models")
include(":core:navigation")
include(":core:resources")

// Feature Modules
include(":feature:home")
include(":feature:onboarding")
include(":feature:editor")
include(":feature:terminal")
include(":feature:fileexplorer")
include(":feature:git")
include(":feature:settings")
