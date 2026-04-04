package com.mobilecodespace.feature.terminal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun TerminalScreen(viewModel: TerminalViewModel) {
    // Dark Purple Theme Hintergrund
    val darkPurple = Color(0xFF2D1B4E)

    Column(modifier = Modifier.fillMaxSize().background(darkPurple)) {
        // Terminal View Container
        Box(modifier = Modifier.weight(1f).padding(8.dp)) {
            AndroidView(
                factory = { context ->
                    // Hier würde die Initialisierung der Termux-View erfolgen
                    // TerminalView(context).apply { ... }
                    android.widget.TextView(context).apply {
                        text = "root@mobilecodespace:~$ "
                        setTextColor(android.graphics.Color.WHITE)
                        typeface = android.graphics.Typeface.MONOSPACE
                        textSize = 14f
                    }
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
            Button(
                onClick = { onKeyClick(keys[index]) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4A2C7A))
            ) {
                Text(text = keys[index], color = Color.White)
            }
        }
    }
}
