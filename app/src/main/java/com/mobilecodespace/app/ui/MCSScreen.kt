package com.mobilecodespace.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MCSScreen(
    viewModel: MCSViewModel = viewModel()
) {
    val isSetupComplete by viewModel.isSetupComplete.collectAsState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (isSetupComplete) {
            // Hier wird später die Navigation zum Home-Feature eingebunden
            Text(text = "Home Screen (Feature:Home)")
        } else {
            // Hier wird später die Navigation zum Onboarding-Feature eingebunden
            Text(text = "Onboarding Screen (Feature:Onboarding)")
        }
    }
}
