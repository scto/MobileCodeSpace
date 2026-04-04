package com.mobilecodespace.feature.fileexplorer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FileExplorerScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("File Explorer", style = MaterialTheme.typography.headlineMedium)
        LazyColumn {
            // Hier würde die Logik zum Auflisten der Dateien folgen
            item { Text("Project Root/") }
            item { Text("  src/") }
            item { Text("  build.gradle.kts") }
            item { Text("  README.md") }
        }
    }
}
