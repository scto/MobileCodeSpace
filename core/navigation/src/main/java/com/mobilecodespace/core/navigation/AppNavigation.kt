package com.mobilecodespace.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mobilecodespace.feature.editor.EditorScreen
import com.mobilecodespace.feature.fileexplorer.FileExplorerScreen
import com.mobilecodespace.feature.git.GitScreen
import com.mobilecodespace.feature.home.HomeScreen
import com.mobilecodespace.feature.onboarding.OnboardingScreen

@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.Onboarding.route) { 
            OnboardingScreen(viewModel = hiltViewModel()) 
        }
        composable(Screen.Home.route) { 
            HomeScreen(viewModel = hiltViewModel()) 
        }
        composable(Screen.Editor.route) { 
            EditorScreen(viewModel = hiltViewModel()) 
        }
        composable(Screen.FileExplorer.route) {
            FileExplorerScreen()
        }
        composable(Screen.Git.route) {
            GitScreen()
        }
    }
}
