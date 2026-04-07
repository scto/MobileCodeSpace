package com.mobilecodespace.core.exec

import java.util.concurrent.TimeUnit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Hilfsklasse zur Ausführung von Shell-Befehlen.
 */
object ShellUtils {
    data class Result(val exitCode: Int, val output: String, val error: String, val timedOut: Boolean)

    /**
     * Führt einen Befehl außerhalb der Sandbox aus.
     */
    suspend fun run(vararg command: String, timeoutSeconds: Long? = null): Result =
        withContext(Dispatchers.IO) {
            val process = ProcessBuilder(*command).start()

            val output = StringBuilder()
            val error = StringBuilder()

            val outputThread = Thread {
                runCatching { process.inputStream.bufferedReader().forEachLine { output.appendLine(it) } }
            }
            val errorThread = Thread {
                runCatching { process.errorStream.bufferedReader().forEachLine { error.appendLine(it) } }
            }

            outputThread.start()
            errorThread.start()

            val timedOut =
                if (timeoutSeconds != null) {
                    !process.waitFor(timeoutSeconds, TimeUnit.SECONDS)
                } else {
                    process.waitFor()
                    false
                }

            if (timedOut) {
                process.destroyForcibly()
            }

            outputThread.join()
            errorThread.join()

            Result(
                exitCode = if (timedOut) -1 else process.exitValue(),
                output = output.toString().trim(),
                error = error.toString().trim(),
                timedOut = timedOut,
            )
        }

    /**
     * Führt einen Befehl innerhalb der Ubuntu/PRoot-Umgebung aus.
     */
    suspend fun runUbuntu(workingDir: String? = null, vararg command: String, timeoutSeconds: Long? = null): Result =
        withContext(Dispatchers.IO) {
            val process = ubuntuProcess(workingDir = workingDir, command = command.toList())

            val output = StringBuilder()
            val error = StringBuilder()

            val outputThread = Thread {
                runCatching { process.inputStream.bufferedReader().forEachLine { output.appendLine(it) } }
            }
            val errorThread = Thread {
                runCatching { process.errorStream.bufferedReader().forEachLine { error.appendLine(it) } }
            }

            outputThread.start()
            errorThread.start()

            val timedOut =
                if (timeoutSeconds != null) {
                    !process.waitFor(timeoutSeconds, TimeUnit.SECONDS)
                } else {
                    process.waitFor()
                    false
                }

            if (timedOut) {
                process.destroyForcibly()
            }

            outputThread.join()
            errorThread.join()

            Result(
                exitCode = if (timedOut) -1 else process.exitValue(),
                output = output.toString().trim(),
                error = error.toString().trim(),
                timedOut = timedOut,
            )
        }
}
