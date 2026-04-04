package com.mobilecodespace.core.navigation

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
    object Editor : Screen("editor")
    object FileExplorer : Screen("fileexplorer")
    object Git : Screen("git")
}
