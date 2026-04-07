package com.mobilecodespace.feature.editor

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.mobilecodespace.core.utils.Environment
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.langs.text.TextLanguage
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import java.io.File

@Composable
fun SoraEditorView(
    viewModel: EditorViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    
    val editor = remember {
        CodeEditor(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            
            // Konfiguration: Laden von Themes aus MOBILECODESPACE_HOME
            val themeDir = File(Environment.MOBILECODESPACE_HOME, "themes")
            if (themeDir.exists() && themeDir.isDirectory) {
                // Beispiel: Laden eines Standard-Themes, falls vorhanden
                val defaultTheme = File(themeDir, "default.json")
                if (defaultTheme.exists()) {
                    // Hier würde die Logik zum Parsen des JSON-Themes und Setzen des ColorScheme stehen
                    // editor.colorScheme = EditorColorScheme.load(defaultTheme)
                }
            }
            
            // Standard-Sprache setzen
            setEditorLanguage(TextLanguage())
        }
    }

    DisposableEffect(Unit) {
        viewModel.setEditor(editor)
        onDispose { }
    }

    AndroidView(
        factory = { editor },
        modifier = modifier
    )
}
