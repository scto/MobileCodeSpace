package com.mobilecodespace.feature.editor.lsp

import io.github.rosemoe.sora.lsp.LspClient
import java.io.File

class LspManager {
    private var lspClient: LspClient? = null
    private var diagnosticListener: ((List<String>) -> Unit)? = null

    fun setDiagnosticListener(listener: (List<String>) -> Unit) {
        this.diagnosticListener = listener
    }

    fun start(language: String, file: File) {
        // Hier würde die tatsächliche Initialisierung des LSP-Servers erfolgen
        // lspClient = LspClient.create(...)
        // lspClient?.didOpen(file, language)
        
        // Beispiel für einen Callback, wenn Diagnostics vom Server kommen:
        // lspClient?.onDiagnostics { diagnostics ->
        //     diagnosticListener?.invoke(diagnostics.map { it.message })
        // }
    }

    fun format(file: File) {
        // lspClient?.format(file)
    }

    fun shutdown() {
        lspClient?.shutdown()
        lspClient = null
    }
}
