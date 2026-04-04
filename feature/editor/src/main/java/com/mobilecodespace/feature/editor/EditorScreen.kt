package com.mobilecodespace.feature.editor

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme

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
            // Wir nutzen das ThemeRegistry, um das Dark Purple Theme zu laden
            val themeRegistry = ThemeRegistry.getInstance()
            // Annahme: Die Datei liegt in assets/themes/dark_purple.json
            try {
                context.assets.open("themes/dark_purple.json").use {
                    themeRegistry.loadTheme(it)
                }
                colorScheme = themeRegistry.getColorScheme("dark_purple")
            } catch (e: Exception) {
                colorScheme = EditorColorScheme() // Fallback
            }
            
            // 2. TextMate & GrammarProvider Integration
            // Wir registrieren einen FileProvider, damit TextMate Grammatiken laden kann
            FileProviderRegistry.getInstance().setContext(context)
            
            // Beispiel für Java-Grammatik (wird durch TextMateLanguage geladen)
            val language = TextMateLanguage.create("source.java", true)
            setEditorLanguage(language)
            
            // 3. LSP Integration (Vorbereitung)
            // Hier würde der LSP-Provider initialisiert werden, sobald die LSP-Bibliothek eingebunden ist
        }
    }

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
