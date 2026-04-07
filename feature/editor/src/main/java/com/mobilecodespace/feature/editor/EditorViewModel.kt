package com.mobilecodespace.feature.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilecodespace.core.utils.Environment
import com.mobilecodespace.core.utils.JsonUtils
import com.mobilecodespace.feature.editor.lsp.LspManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.rosemoe.sora.widget.CodeEditor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

data class EditorUiState(
    val content: String = "",
    val filePath: String? = null,
    val isDirty: Boolean = false,
    val language: String = "text",
    val diagnostics: List<String> = emptyList()
)

@HiltViewModel
class EditorViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(EditorUiState())
    val uiState: StateFlow<EditorUiState> = _uiState.asStateFlow()

    private var editor: CodeEditor? = null
    private val lspManager = LspManager()

    init {
        lspManager.setDiagnosticListener { newDiagnostics ->
            _uiState.value = _uiState.value.copy(diagnostics = newDiagnostics)
        }
    }

    fun setEditor(editor: CodeEditor) {
        this.editor = editor
    }

    fun openFile(file: File) {
        viewModelScope.launch {
            val content = withContext(Dispatchers.IO) {
                if (file.exists()) file.readText() else ""
            }
            editor?.setText(content)
            
            val language = when (file.extension) {
                "java" -> "java"
                "kt" -> "kotlin"
                "cpp", "c" -> "cpp"
                "xml" -> "xml"
                "sh" -> "bash"
                else -> "text"
            }
            
            lspManager.start(language, file)
            
            _uiState.value = EditorUiState(
                content = content,
                filePath = file.absolutePath,
                isDirty = false,
                language = language
            )
        }
    }

    fun saveFile() {
        val path = _uiState.value.filePath ?: return
        val file = File(path)
        val content = editor?.text.toString()
        
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                file.writeText(content)
                
                // Metadaten-Speicherung via JsonUtils im .mcs Ordner
                val metadata = mapOf(
                    "lastSaved" to System.currentTimeMillis(),
                    "filePath" to path
                )
                val metaFile = File(Environment.MOBILECODESPACE_HOME, "${file.name}.json")
                JsonUtils.saveToFile(metadata, metaFile.absolutePath)
            }
            _uiState.value = _uiState.value.copy(isDirty = false)
        }
    }

    fun formatCode() {
        _uiState.value.filePath?.let { path ->
            lspManager.format(File(path))
        }
    }
    
    override fun onCleared() {
        super.onCleared()
        lspManager.shutdown()
    }
}
