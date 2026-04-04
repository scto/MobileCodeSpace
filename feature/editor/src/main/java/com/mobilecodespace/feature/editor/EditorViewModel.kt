package com.mobilecodespace.feature.editor

import androidx.lifecycle.ViewModel
import io.github.rosemoe.sora.widget.CodeEditor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

class EditorViewModel : ViewModel() {

    private val _openFiles = MutableStateFlow<List<File>>(emptyList())
    val openFiles: StateFlow<List<File>> = _openFiles

    private val _isSaved = MutableStateFlow(true)
    val isSaved: StateFlow<Boolean> = _isSaved

    // Referenz auf den Editor, um Aktionen auszuführen
    private var editor: CodeEditor? = null

    fun setEditor(editor: CodeEditor) {
        this.editor = editor
    }

    fun openFile(file: File) {
        if (!_openFiles.value.contains(file)) {
            _openFiles.value = _openFiles.value + file
        }
        // TODO: Inhalt der Datei in den Editor laden
    }

    fun saveFile(content: String) {
        // Logik zum Speichern der Datei
        _isSaved.value = true
    }

    fun saveAll() {
        // Logik zum Speichern aller offenen Dateien
        _isSaved.value = true
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

    fun paste() {
        editor?.paste()
    }

    fun formatCode() {
        // Logik für Code-Formatierung (z.B. via LSP)
    }
}
