package com.mobilecodespace.core.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mobilecodespace.feature.editor.EditorScreen
import com.mobilecodespace.feature.fileexplorer.FileExplorerScreen
import com.mobilecodespace.feature.git.GitScreen
import com.mobilecodespace.feature.home.HomeScreen
import com.mobilecodespace.feature.onboarding.OnboardingScreen
import java.net.URLDecoder

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
        composable(
            route = Screen.Editor.route,
            arguments = listOf(navArgument("filePath") { type = NavType.StringType })
        ) { backStackEntry ->
            val encodedPath = backStackEntry.arguments?.getString("filePath") ?: ""
            val filePath = URLDecoder.decode(encodedPath, "UTF-8")
            EditorScreen(viewModel = hiltViewModel(), filePath = filePath) 
        }
        composable(Screen.FileExplorer.route) {
            FileExplorerScreen(onFileClick = { filePath ->
                navController.navigate(Screen.Editor.createRoute(filePath))
            })
        }
        composable(Screen.Git.route) {
            GitScreen()
        }
    }
}
