package com.mobilecodespace.feature.terminal.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TerminalToolbar(onKeyClick: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { onKeyClick("ESC") }) { Text("ESC") }
        Button(onClick = { onKeyClick("CTRL") }) { Text("CTRL") }
        Button(onClick = { onKeyClick("ALT") }) { Text("ALT") }
        Button(onClick = { onKeyClick("TAB") }) { Text("TAB") }
    }
}
