package com.mobilecodespace.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobilecodespace.feature.editor.EditorScreen
import com.mobilecodespace.feature.home.HomeScreen
import com.mobilecodespace.feature.onboarding.OnboardingScreen

@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable("onboarding") { 
            OnboardingScreen(viewModel = hiltViewModel()) 
        }
        composable("home") { 
            HomeScreen(viewModel = hiltViewModel()) 
        }
        composable("editor") { 
            EditorScreen(viewModel = hiltViewModel()) 
        }
    }
}
