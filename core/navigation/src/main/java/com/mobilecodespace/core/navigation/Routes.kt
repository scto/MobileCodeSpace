package com.mobilecodespace.core.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Editor : Screen("editor/{filePath}") {
        fun createRoute(filePath: String) = "editor/${java.net.URLEncoder.encode(filePath, "UTF-8")}"
    }
    object FileExplorer : Screen("fileexplorer")
    object Git : Screen("git")
}
