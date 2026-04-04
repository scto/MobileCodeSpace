package com.mobilecodespace.feature.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mobilecodespace.core.ui.components.MCSButton
import com.mobilecodespace.core.ui.components.MCSProgressBar

@Composable
fun OnboardingScreen(viewModel: OnboardingViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (uiState) {
            is OnboardingUiState.Permissions -> {
                Text("Berechtigungen erforderlich")
                Spacer(modifier = Modifier.height(16.dp))
                MCSButton(text = "Berechtigungen erteilen", onClick = { viewModel.requestPermissions() })
            }
            is OnboardingUiState.Downloading -> {
                Text("Lade Dateien herunter...")
                Spacer(modifier = Modifier.height(16.dp))
                MCSProgressBar(progress = 0.5f)
                Spacer(modifier = Modifier.height(16.dp))
                MCSButton(text = "Start Download", onClick = { viewModel.startDownload() })
            }
            is OnboardingUiState.Installing -> {
                Text("Installiere Umgebung...")
                Spacer(modifier = Modifier.height(16.dp))
                MCSProgressBar(progress = 0.8f)
            }
            is OnboardingUiState.Completed -> {
                Text("Setup abgeschlossen!")
            }
        }
    }
}
