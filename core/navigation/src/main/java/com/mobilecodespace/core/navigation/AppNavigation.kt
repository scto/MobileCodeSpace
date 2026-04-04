package com.mobilecodespace.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable("onboarding") { /* OnboardingScreen */ }
        composable("home") { /* HomeScreen */ }
        composable("editor") { /* EditorScreen */ }
    }
}
