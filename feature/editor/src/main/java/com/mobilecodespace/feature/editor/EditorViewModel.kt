package com.mobilecodespace.feature.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.rosemoe.sora.widget.CodeEditor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor() : ViewModel() {

    private val _openFiles = MutableStateFlow<List<File>>(emptyList())
    val openFiles: StateFlow<List<File>> = _openFiles

    private val _isSaved = MutableStateFlow(true)
    val isSaved: StateFlow<Boolean> = _isSaved

    private var editor: CodeEditor? = null
    private var currentFile: File? = null

    fun setEditor(editor: CodeEditor) {
        this.editor = editor
    }

    fun openFile(file: File) {
        viewModelScope.launch {
            val content = withContext(Dispatchers.IO) {
                file.readText()
            }
            editor?.setText(content)
            currentFile = file
            
            // Sprach-Erkennung basierend auf Dateiendung
            val language = when (file.extension) {
                "java" -> "java"
                "kt" -> "kotlin"
                "cpp", "c" -> "cpp"
                else -> "text"
            }
            
            // LSP-Initialisierung für die erkannte Sprache
            startLsp(language)
            
            if (!_openFiles.value.contains(file)) {
                _openFiles.value = _openFiles.value + file
            }
            _isSaved.value = true
        }
    }

    private fun startLsp(language: String) {
        // TODO: LSP-Client für die Sprache starten
    }

    fun saveFile() {
        val file = currentFile ?: return
        val content = editor?.text.toString()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                file.writeText(content)
            }
            _isSaved.value = true
        }
    }

    fun saveAll() {
        saveFile()
    }

    fun undo() {
        editor?.undo()
    }

    fun redo() {
        editor?.redo()
    }

    fun selectAll() {
        editor?.selectAll()
    }

    fun cut() {
        editor?.cut()
    }

    fun copy() {
        editor?.copy()
    }

    fun paste() {
        editor?.paste()
    }

    fun formatCode() {
        // LSP-Integration für Formatierung
    }
}
