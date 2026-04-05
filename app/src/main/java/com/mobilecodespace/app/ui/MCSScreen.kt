package com.mobilecodespace.app.ui

import android.os.Build
import android.os.Environment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.mobilecodespace.feature.home.HomeScreen
import com.mobilecodespace.feature.home.HomeViewModel
import com.mobilecodespace.feature.onboarding.OnboardingScreen
import com.mobilecodespace.feature.onboarding.OnboardingViewModel

@Composable
fun MCSScreen(
    viewModel: MCSViewModel = hiltViewModel()
) {
    val isSetupComplete by viewModel.isSetupComplete.collectAsState()
    val context = LocalContext.current
    
    // Check if MANAGE_EXTERNAL_STORAGE is granted
    val hasPermission = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            true // For older versions, permission is handled differently or not required in this scope
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isSetupComplete && hasPermission) {
            // Show Home Screen
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(viewModel = homeViewModel)
        } else {
            // Show Onboarding Screen
            val onboardingViewModel: OnboardingViewModel = hiltViewModel()
            OnboardingScreen(viewModel = onboardingViewModel)
        }
    }
}
