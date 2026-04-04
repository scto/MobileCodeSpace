package com.mobilecodespace.feature.terminal

import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.mobilecodespace.feature.terminal.components.TerminalToolbar
import com.termux.view.TerminalView

@Composable
fun TerminalScreen(viewModel: TerminalViewModel) {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            factory = { ctx ->
                TerminalView(ctx, null).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    // In a real implementation, you would attach a TerminalSession here
                    // that reads from viewModel.inputStream and writes to viewModel.outputStream
                }
            }
        )
        
        TerminalToolbar(
            onKeyClick = { key -> viewModel.sendKey(key) }
        )
    }
}
