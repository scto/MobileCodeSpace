package com.mobilecodespace.feature.terminal

import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.mobilecodespace.feature.terminal.components.TerminalToolbar
import com.termux.terminal.TerminalSession
import com.termux.view.TerminalView

@Composable
fun TerminalScreen(viewModel: TerminalViewModel) {
    val context = LocalContext.current

    val terminalView = remember {
        TerminalView(context, null).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    DisposableEffect(viewModel) {
        // In einer echten Implementierung würde hier eine TerminalSession erstellt
        // und mit viewModel.inputStream und viewModel.outputStream verbunden werden.
        // Da TerminalSession eine komplexe Klasse aus der Termux-Library ist,
        // wird hier die Verbindung angedeutet.
        
        onDispose {
            // Session aufräumen
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            factory = { terminalView }
        )
        
        TerminalToolbar(
            onKeyClick = { key -> viewModel.sendKey(key) }
        )
    }
}
