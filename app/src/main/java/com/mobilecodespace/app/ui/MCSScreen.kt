package com.mobilecodespace.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilecodespace.feature.onboarding.OnboardingScreen
import com.mobilecodespace.feature.onboarding.OnboardingViewModel

@Composable
fun MCSScreen(
    viewModel: MCSViewModel = viewModel()
) {
    val isSetupComplete by viewModel.isSetupComplete.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (isSetupComplete) {
            // Hier wird später die Navigation zum Home-Feature eingebunden
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Home Screen (Feature:Home)")
            }
        } else {
            // Integration des Onboarding-Screens
            val onboardingViewModel: OnboardingViewModel = hiltViewModel()
            OnboardingScreen(viewModel = onboardingViewModel)
        }
    }
}
