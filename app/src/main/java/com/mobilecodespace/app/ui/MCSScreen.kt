package com.mobilecodespace.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MCSScreen(
    viewModel: MCSViewModel = viewModel()
) {
    // Hier wird später die Navigationslogik implementiert, 
    // die zwischen Onboarding und Home entscheidet.
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "MobileCodeSpace Initializing...")
    }
}
