package com.mobilecodespace.feature.editor.lsp

import com.mobilecodespace.core.utils.Environment
import com.mobilecodespace.core.utils.FileUtils
import io.github.rosemoe.sora.lsp.LspClient
import java.io.File

class LspManager {
    private var lspClient: LspClient? = null
    private var diagnosticListener: ((List<String>) -> Unit)? = null

    fun setDiagnosticListener(listener: (List<String>) -> Unit) {
        this.diagnosticListener = listener
    }

    fun start(language: String, file: File) {
        // Suche nach dem LSP-Server Binary im LSP_DIR
        // Annahme: Binaries sind benannt als lsp-<language>
        val binary = File(Environment.LSP_DIR, "lsp-$language")
        
        if (binary.exists()) {
            FileUtils.setFileExecutable(binary)
            
            // Initialisierung des LSP-Clients
            lspClient = LspClient.create(binary.absolutePath)
            
            lspClient?.didOpen(file, language)
            
            lspClient?.onDiagnostics { diagnostics ->
                diagnosticListener?.invoke(diagnostics.map { it.message })
            }
        }
    }

    fun format(file: File) {
        lspClient?.format(file)
    }

    fun shutdown() {
        lspClient?.shutdown()
        lspClient = null
    }
}
