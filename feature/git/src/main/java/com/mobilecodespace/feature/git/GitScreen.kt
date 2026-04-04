package com.mobilecodespace.feature.git

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GitScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Git Status", style = MaterialTheme.typography.headlineMedium)
        Text("On branch main")
        Text("Changes not staged for commit:")
        Text("  modified: src/main/java/MainActivity.kt")
        Text("  modified: build.gradle.kts")
        Text("  modified: feature/git/GitScreen.kt")
    }
}
