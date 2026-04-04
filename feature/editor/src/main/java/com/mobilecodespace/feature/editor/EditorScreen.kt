package com.mobilecodespace.feature.editor

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import io.github.rosemoe.sora.langs.textmate.TextMateLanguage
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
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
            
            // 1. Theme Integration via AssetManager
            // Hier würde man das Theme aus den Assets laden:
            // val themeRegistry = ThemeRegistry.getInstance()
            // themeRegistry.loadTheme(context.assets.open("themes/dark_purple.json"))
            colorScheme = EditorColorScheme() 
            
            // 2. TextMate & GrammarProvider Integration
            // Beispiel für Java-Grammatik
            val language = TextMateLanguage.create("source.java", true)
            setEditorLanguage(language)
            
            // 3. LSP Integration (Vorbereitung)
            // Hier würde der LSP-Provider initialisiert werden:
            // val lspProvider = LspLanguageProvider(...)
            // setEditorLanguage(lspProvider)
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
