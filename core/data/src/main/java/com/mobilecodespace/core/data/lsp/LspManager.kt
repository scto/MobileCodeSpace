package com.mobilecodespace.core.data.lsp

import com.mcs.core.utils.Environment
import com.mcs.core.utils.FileUtils
import com.mcs.core.utils.ArchiveUtils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

data class LspPlugin(
    val id: String,
    val name: String,
    val version: String,
    val downloadUrl: String,
    val binaryPath: String
)

@Singleton
class LspManager @Inject constructor() {

    private val _installedPlugins = MutableStateFlow<List<LspPlugin>>(emptyList())
    val installedPlugins: StateFlow<List<LspPlugin>> = _installedPlugins

    fun installPlugin(plugin: LspPlugin, zipFile: File): Boolean {
        val targetDir = File(Environment.LSP_DIR, plugin.id)
        if (!targetDir.exists()) targetDir.mkdirs()

        // Entpacken
        val success = ArchiveUtils.extract(zipFile.absolutePath, targetDir.absolutePath)
        if (!success) return false

        // Binary ausführbar machen
        val binary = File(targetDir, plugin.binaryPath)
        if (binary.exists()) {
            FileUtils.setFileExecutable(binary)
        }

        // Update State
        _installedPlugins.value = _installedPlugins.value + plugin
        return true
    }

    fun uninstallPlugin(plugin: LspPlugin) {
        val targetDir = File(Environment.LSP_DIR, plugin.id)
        if (targetDir.exists()) {
            targetDir.deleteRecursively()
        }
        _installedPlugins.value = _installedPlugins.value.filter { it.id != plugin.id }
    }
}
