package com.mobilecodespace.feature.editor

import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.langs.treesitter.TreeSitterLanguage
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(viewModel: EditorViewModel) {
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
            
            // Beispiel für Tree-sitter Integration (Java)
            // In einer echten App würden hier die Tree-sitter Grammatiken geladen
            val language = TreeSitterLanguage.create("java")
            setEditorLanguage(language)
            
            // 3. LSP Integration (Vorbereitung)
            // Hier würde der LspLanguageProvider initialisiert werden
            // val lspProvider = LspLanguageProvider(...)
            // setEditorLanguage(lspProvider)
        }
    }

    DisposableEffect(editor) {
        viewModel.setEditor(editor)
        onDispose { }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editor") },
                actions = {
                    IconButton(onClick = { viewModel.saveFile() }) {
                        Icon(Icons.Default.Check, contentDescription = "Speichern")
                    }
                    IconButton(onClick = { viewModel.undo() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Rückgängig")
                    }
                    IconButton(onClick = { viewModel.formatCode() }) {
                        Icon(Icons.Default.Edit, contentDescription = "Formatieren")
                    }
                }
            )
        }
    ) { paddingValues ->
        AndroidView(
            factory = { editor },
            modifier = Modifier.padding(paddingValues),
            update = { view ->
                // Hier könnten LSP-Provider oder Grammar-Provider bei State-Änderungen aktualisiert werden
            }
        )
    }
}
