package com.mobilecodespace.core.exec

import android.content.Context
import android.content.Intent
import com.mobilecodespace.core.utils.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Hilfsklasse für Terminal-Operationen.
 */

/**
 * Prüft, ob das Terminal installiert ist.
 */
fun isTerminalInstalled(): Boolean {
    val rootfs =
        Environment.ROOTFS.listFiles()?.filter {
            it.absolutePath != Environment.HOME.absolutePath &&
                it.absolutePath != Environment.TMP_DIR.absolutePath
        } ?: emptyList()

    return Environment.BIN_DIR.child(".terminal_setup_ok_DO_NOT_REMOVE").exists() && rootfs.isNotEmpty()
}

/**
 * Prüft, ob das Terminal funktionsfähig ist.
 */
suspend fun isTerminalWorking(): Boolean =
    withContext(Dispatchers.IO) {
        val process = ubuntuProcess(command = listOf("true"))
        return@withContext process.waitFor() == 0
    }

/**
 * Startet das Terminal.
 * Hinweis: Diese Funktion benötigt UI-Kontext und sollte in der UI-Schicht aufgerufen werden.
 */
fun launchTerminal(context: Context, terminalCommand: TerminalCommand) {
    // Die UI-Logik (showTerminalNotice) muss in der UI-Schicht implementiert werden.
    // Hier wird nur die Vorbereitung für den Start des Terminals abgebildet.
    // context.startActivity(Intent(context, Terminal::class.java))
}
