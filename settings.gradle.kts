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
include(":core:ui", ":core:utils", ":core:domain", ":core:navigation", ":core:data", ":core:resources", ":core:exec")
include(":feature:home", ":feature:onboarding", ":feature:editor", ":feature:terminal", ":feature:fileexplorer", ":feature:git", ":feature:settings", ":feature:file-tree", ":feature:workspace", ":feature:search", ":feature:terminal-view")
