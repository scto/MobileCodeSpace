package com.mobilecodespace.feature.terminal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
// Hinweis: Hier würde die Termux-View Bibliothek importiert werden
// import com.termux.view.TerminalView 

@Composable
fun TerminalScreen(viewModel: TerminalViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Terminal View Container
        Box(modifier = Modifier.weight(1f)) {
            AndroidView(
                factory = { context ->
                    // Hier würde die Initialisierung der Termux-View erfolgen
                    // TerminalView(context).apply { ... }
                    android.widget.TextView(context).apply { text = "Terminal Placeholder" }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // Custom Keyboard Bar
        KeyboardBar(onKeyClick = { key ->
            viewModel.sendInput(key)
        })
    }
}

@Composable
fun KeyboardBar(onKeyClick: (String) -> Unit) {
    val keys = listOf("CTRL", "ESC", "TAB", "↑", "↓", "←", "→")
    
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(keys.size) { index ->
            Button(onClick = { onKeyClick(keys[index]) }) {
                Text(text = keys[index])
            }
        }
    }
}
