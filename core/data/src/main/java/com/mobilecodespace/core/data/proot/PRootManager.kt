package com.mobilecodespace.core.data.proot

import android.content.Context
import java.io.File
import java.io.FileOutputStream

/**
 * Verwaltet den PRoot-Prozess und das Ubuntu-Rootfs.
 * Verantwortlich für das Entpacken der Binary und das Starten der Shell.
 */
class PRootManager(private val context: Context) {

    private val prootDir = File(context.filesDir, "proot")
    private val prootBinary = File(prootDir, "proot-arm64")
    private val rootfsDir = File(prootDir, "ubuntu-rootfs")

    init {
        if (!prootDir.exists()) prootDir.mkdirs()
    }

    /**
     * Kopiert die proot-Binary aus den Assets in das interne Verzeichnis,
     * falls sie noch nicht existiert, und setzt die Ausführungsrechte.
     */
    fun installProotBinary() {
        if (!prootBinary.exists()) {
            context.assets.open("proot-arm64").use { input ->
                FileOutputStream(prootBinary).use { output ->
                    input.copyTo(output)
                }
            }
            prootBinary.setExecutable(true)
        }
    }

    /**
     * Bereitet das Rootfs vor.
     * TODO: Implementierung für Download und Extraktion (z.B. via Tar) hinzufügen.
     */
    fun setupRootfs() {
        if (!rootfsDir.exists()) {
            // Hier würde die Logik für Download und Extraktion (z.B. via Tar) folgen
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
