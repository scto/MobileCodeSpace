package com.mobilecodespace.feature.editor

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

class EditorViewModel : ViewModel() {

    private val _openFiles = MutableStateFlow<List<File>>(emptyList())
    val openFiles: StateFlow<List<File>> = _openFiles

    private val _isSaved = MutableStateFlow(true)
    val isSaved: StateFlow<Boolean> = _isSaved

    fun openFile(file: File) {
        // Logik zum Öffnen der Datei
        _openFiles.value = _openFiles.value + file
    }

    fun saveFile(content: String) {
        // Logik zum Speichern
        _isSaved.value = true
    }

    fun undo() {
        // Logik für Undo
    }

    fun redo() {
        // Logik für Redo
    }
}
