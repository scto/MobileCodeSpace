package com.mobilecodespace.feature.editor.lsp

import io.github.rosemoe.sora.lsp.LspClient
import java.io.File

class LspManager {
    private var lspClient: LspClient? = null

    fun start(language: String, file: File) {
        // Hier würde die tatsächliche Initialisierung des LSP-Servers erfolgen
        // lspClient = LspClient.create(...)
        // lspClient?.didOpen(file, language)
    }

    fun format(file: File) {
        // lspClient?.format(file)
    }

    fun shutdown() {
        lspClient?.shutdown()
        lspClient = null
    }
}
