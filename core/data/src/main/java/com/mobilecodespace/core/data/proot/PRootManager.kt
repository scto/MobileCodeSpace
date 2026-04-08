package com.mobilecodespace.core.data.proot

import android.content.Context
import com.mobilecodespace.core.utils.Environment
import com.mobilecodespace.core.utils.FileUtils
import java.io.File
import java.io.FileOutputStream

/**
 * Verwaltet den PRoot-Prozess und das Ubuntu-Rootfs.
 * Verantwortlich für das Entpacken der Binary und das Starten der Shell.
 */
class PRootManager(private val context: Context) {

    private val prootBinary = File(Environment.BIN_DIR, "proot")
    private val rootfsDir = Environment.ROOTFS

    init {
        Environment.ensureDirectories()
    }

    /**
     * Kopiert die proot-Binary aus den Assets in das interne Verzeichnis,
     * falls sie noch nicht existiert, und setzt die Ausführungsrechte.
     */
    fun installProotBinary() {
        val arch = if (FileUtils.isAarch64()) "arm64" else "arm"
        val assetPath = "terminal/$arch/proot"
        
        if (!prootBinary.exists()) {
            context.assets.open(assetPath).use { input ->
                FileOutputStream(prootBinary).use { output ->
                    input.copyTo(output)
                }
            }
            FileUtils.setFileExecutable(prootBinary)
        }
    }

    /**
     * Bereitet das Rootfs vor.
     */
    fun setupRootfs() {
        if (!rootfsDir.exists()) {
            rootfsDir.mkdirs()
        }
    }

    /**
     * Startet den PRoot-Prozess mit den notwendigen Parametern.
     */
    fun startProot(): Process {
        val command = listOf(
            prootBinary.absolutePath,
            "-0",
            "-b", "/sdcard",
            "-r", rootfsDir.absolutePath,
            "/bin/bash"
        )
        
        return ProcessBuilder(command)
            .redirectErrorStream(true)
            .start()
    }
}
