package com.mobilecodespace.feature.editor

import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.mobilecodespace.feature.editor.components.EditorToolbar
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(viewModel: EditorViewModel, filePath: String?) {
    val context = LocalContext.current
    
    val editor = remember {
        CodeEditor(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            
            isLineNumberEnabled = true
            isWordwrapEnabled = true
            
            // 1. Theme Integration
            val themeRegistry = ThemeRegistry.getInstance()
            try {
                context.assets.open("themes/dark_purple.json").use {
                    themeRegistry.loadTheme(it)
                }
                colorScheme = themeRegistry.getColorScheme("dark_purple")
            } catch (e: Exception) {
                colorScheme = EditorColorScheme() // Fallback
            }
            
            // 2. TextMate & GrammarProvider Integration
            FileProviderRegistry.getInstance().setContext(context)
        }
    }

    LaunchedEffect(filePath) {
        if (!filePath.isNullOrEmpty()) {
            viewModel.openFile(File(filePath))
        }
    }

    DisposableEffect(editor) {
        viewModel.setEditor(editor)
        onDispose { }
    }

    Scaffold(
        topBar = {
            EditorToolbar(
                onSave = { viewModel.saveFile() },
                onUndo = { viewModel.undo() },
                onRedo = { viewModel.redo() },
                onFormat = { viewModel.formatCode() },
                onSelectAll = { viewModel.selectAll() },
                onCut = { viewModel.cut() },
                onCopy = { viewModel.copy() },
                onPaste = { viewModel.paste() }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            AndroidView(
                factory = { editor },
                modifier = Modifier.fillMaxSize(),
                update = { view ->
                }
            )
        }
    }
}
