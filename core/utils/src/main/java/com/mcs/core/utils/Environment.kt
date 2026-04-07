package com.mcs.core.utils

import android.content.Context
import android.os.Build
import java.io.File

object Environment {
    private lateinit var appFilesDir: File
    var archApp: String = "64"

    fun init(context: Context) {
        appFilesDir = context.filesDir
        archApp = if (Build.SUPPORTED_ABIS.contains("arm64-v8a")) "64" else "32"
        
        // Erstelle die Ordnerstruktur
        ensureDirectories()
    }

    val HOME: File get() = File(appFilesDir, "MobileCodeSpace")
    val MOBILECODESPACE_HOME: File get() = HOME
    val USR: File get() = File(HOME, "usr")
    val BIN_DIR: File get() = File(USR, "bin")
    val LIB_DIR: File get() = File(USR, "lib")
    val DISTROS: File get() = File(HOME, "distros")
    val ROOTFS: File get() = File(HOME, "rootfs")
    val SCRIPTS: File get() = File(HOME, "scripts")
    val TMP_DIR: File get() = File(HOME, "tmp")
    val LSP_DIR: File get() = File(HOME, "lsp")

    fun ensureDirectories() {
        val dirs = listOf(HOME, USR, BIN_DIR, LIB_DIR, DISTROS, ROOTFS, SCRIPTS, TMP_DIR, LSP_DIR)
        dirs.forEach { if (!it.exists()) it.mkdirs() }
    }

    fun getEnvironment(projectPath: String): Map<String, String> {
        return mapOf(
            "PATH" to "${BIN_DIR.absolutePath}:/usr/bin:/bin",
            "JAVA_HOME" to "/usr/lib/jvm/default-java",
            "ANDROID_HOME" to "/android/sdk",
            "LD_LIBRARY_PATH" to LIB_DIR.absolutePath,
            "LANG" to "de_DE.UTF-8",
            "TERM" to "xterm-256color"
        )
    }
}
