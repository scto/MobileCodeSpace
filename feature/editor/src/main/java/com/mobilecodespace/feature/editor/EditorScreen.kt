package com.mobilecodespace.feature.editor

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import io.github.rosemoe.sora.widget.CodeEditor

@Composable
fun EditorScreen(viewModel: EditorViewModel) {
    val context = LocalContext.current

    AndroidView(
        factory = { ctx ->
            CodeEditor(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                // Hier könnten weitere Konfigurationen wie Themes oder LSP-Provider folgen
            }
        },
        update = { editor ->
            // Update-Logik, falls sich der State ändert
        }
    )
}
