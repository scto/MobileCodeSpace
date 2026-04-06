package com.mobilecodespace.feature.editor

import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.mobilecodespace.core.ui.components.Sidepanel
import com.mobilecodespace.feature.editor.components.EditorToolbar
import com.mobilecodespace.feature.filetree.ui.FileTreeView
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry
import io.github.rosemoe.sora.langs.textmate.registry.ThemeRegistry
import io.github.rosemoe.sora.widget.CodeEditor
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(viewModel: EditorViewModel, filePath: String?) {
    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showMenu by remember { mutableStateOf(false) }
    var showToolsMenu by remember { mutableStateOf(false) }

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

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Sidepanel(
                    mainContent = {
                        val projectRoot = filePath?.let { File(it).parent } ?: ""
                        FileTreeView(
                            projectPath = projectRoot,
                            onFileClick = { file ->
                                viewModel.openFile(file)
                                scope.launch { drawerState.close() }
                            }
                        )
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Editor") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    actions = {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                        }
                        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                            DropdownMenuItem(text = { Text("Öffnen") }, onClick = { /* TODO */ showMenu = false })
                            DropdownMenuItem(text = { Text("Schließen") }, onClick = { /* TODO */ showMenu = false })
                            DropdownMenuItem(text = { Text("Alle schließen") }, onClick = { /* TODO */ showMenu = false })
                            Divider()
                            DropdownMenuItem(text = { Text("Speichern") }, onClick = { viewModel.saveFile(); showMenu = false })
                            DropdownMenuItem(text = { Text("Alles speichern") }, onClick = { viewModel.saveAll(); showMenu = false })
                            Divider()
                            DropdownMenuItem(text = { Text("Tools") }, onClick = { showToolsMenu = true; showMenu = false })
                        }
                        DropdownMenu(expanded = showToolsMenu, onDismissRequest = { showToolsMenu = false }) {
                            DropdownMenuItem(text = { Text("In Datei suchen") }, onClick = { /* TODO */ showToolsMenu = false })
                            DropdownMenuItem(text = { Text("Im Projekt suchen") }, onClick = { /* TODO */ showToolsMenu = false })
                            DropdownMenuItem(text = { Text("Formatieren") }, onClick = { viewModel.formatCode(); showToolsMenu = false })
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
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
                AndroidView(
                    factory = { editor },
                    modifier = Modifier.fillMaxSize(),
                    update = { view ->
                    }
                )
            }
        }
    }
}
