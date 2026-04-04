package com.mobilecodespace.feature.editor

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme

@Composable
fun EditorScreen(viewModel: EditorViewModel) {
    val context = LocalContext.current
    
    // Erstelle den Editor einmalig
    val editor = remember {
        CodeEditor(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            // Grundlegende Konfiguration
            isLineNumberEnabled = true
            isWordwrapEnabled = true
            
            // Initiales Farbschema (Dark Purple Theme Vorbereitung)
            colorScheme = EditorColorScheme() 
        }
    }

    // Verbinde Editor mit ViewModel
    DisposableEffect(editor) {
        viewModel.setEditor(editor)
        onDispose { }
    }

    AndroidView(
        factory = { editor },
        update = { view ->
            // Hier könnten LSP-Provider oder Grammar-Provider bei State-Änderungen aktualisiert werden
        }
    )
}
