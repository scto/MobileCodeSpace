package com.mobilecodespace.core.exec

import android.annotation.SuppressLint
import com.mobilecodespace.core.utils.Environment
import com.mobilecodespace.core.utils.FileUtils
import java.io.File
import java.io.IOException
import java.io.OutputStreamWriter
import kotlin.random.Random
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repräsentiert eine Bindung für das Dateisystem innerhalb der Sandbox.
 */
data class Binding(val outside: String, val inside: String? = null)

private fun MutableList<String>.bind(outside: String, inside: String? = null) {
    if (File(outside).exists()) {
        add("-b")
        add("$outside${if (inside != null){":$inside"}else{""}}")
    }
}

fun List<Binding>.attachTo(list: MutableList<String>, excludeMounts: List<String> = listOf<String>()) {
    forEach {
        if (!excludeMounts.contains(it.outside)) {
            list.bind(it.outside, it.inside)
        }
    }
}

/**
 * Gibt die Standard-Bindungen für die Ubuntu-Umgebung zurück.
 */
fun getDefaultBindings(): List<Binding> {
    fun MutableList<Binding>.bind(outside: String, inside: String? = null) {
        if (File(outside).exists()) {
            add(Binding(outside, inside))
        }
    }

    val list = mutableListOf<Binding>()

    with(list) {
        bind(Environment.HOME.absolutePath, "/home")
        bind("/sdcard")
        bind("/storage")
        bind("/data")
        bind("/dev")
        bind("/proc")
        bind("/system")
        bind("/sys")
        bind("/dev/urandom", "/dev/random")
        bind("/system_ext")
        bind("/product")
        bind("/odm")
        bind("/apex")
        bind("/vendor")
        bind("/linkerconfig/ld.config.txt")
        bind("/linkerconfig/com.android.art/ld.config.txt")
        bind("/plat_property_contexts", "/property_contexts")
        bind(Environment.TMP_DIR.absolutePath, "/dev/shm")
    }

    return list
}

/**
 * Startet einen Prozess innerhalb der Ubuntu/PRoot-Umgebung.
 */
suspend fun ubuntuProcess(
    excludeMounts: List<String> = listOf(),
    root: File = Environment.ROOTFS,
    workingDir: String? = null,
    command: List<String>,
): Process =
    withContext(Dispatchers.IO) {
        if (!root.exists()) throw NoSuchFileException(root)

        val randomInt = Random.nextInt()
        val tmpDir = File(Environment.TMP_DIR, "$randomInt-sandbox")
        if (!tmpDir.exists()) {
            tmpDir.mkdirs()
        }

        val linker = if (File("/system/bin/linker64").exists()) "/system/bin/linker64" else "/system/bin/linker"

        val args =
            mutableListOf<String>().apply {
                add(File(Environment.BIN_DIR, "proot").absolutePath)
                add("--kill-on-exit")

                if (workingDir != null) {
                    add("-w")
                    add(workingDir)
                }

                getDefaultBindings().attachTo(this, excludeMounts)

                bind(tmpDir.absolutePath)

                add("-0")
                add("--link2symlink")
                add("--sysvipc")
                add("-L")

                add("-r")
                add(root.absolutePath)
                addAll(command)
            }

        val processBuilder = ProcessBuilder(linker, *args.toTypedArray())

        processBuilder.environment().let { env ->
            env["WKDIR"] = workingDir.orEmpty()
            env["COLORTERM"] = "truecolor"
            env["TERM"] = "xterm-256color"
            env["LANG"] = "C.UTF-8"
            env["HOME"] = "/home"
            env["PROMPT_DIRTRIM"] = "2"
            env["LINKER"] = linker
            env["TMP_DIR"] = Environment.TMP_DIR.absolutePath
            env["TMPDIR"] = Environment.TMP_DIR.absolutePath
            env["TZ"] = "UTC"
            env["DISPLAY"] = ":0"
            env["LD_LIBRARY_PATH"] = Environment.LIB_DIR.absolutePath
            env["PROOT_TMP_DIR"] = tmpDir.absolutePath

            env["PATH"] =
                "/bin:/sbin:/usr/bin:/usr/sbin:/usr/games:/usr/local/bin:/usr/local/sbin:${Environment.BIN_DIR.absolutePath}:${System.getenv("PATH")}"
        }

        return@withContext processBuilder.start()
    }

@SuppressLint("SdCardPath")
suspend fun ubuntuProcess(
    excludeMounts: List<String> = listOf(),
    root: File = Environment.ROOTFS,
    workingDir: String? = null,
    vararg command: String,
): Process {
    return ubuntuProcess(excludeMounts, root, workingDir, command.toMutableList())
}

/** Extension zum Lesen von stdout als String */
suspend fun Process.readStdout(): String =
    withContext(Dispatchers.IO) {
        try {
            inputStream.bufferedReader().use { reader ->
                if (inputStream.available() <= 0) return@use ""
                reader.readText()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            if (e.message?.contains("Stream closed") == true) "" else throw e
        }
    }

/** Extension zum Lesen von stderr als String */
suspend fun Process.readStderr(): String =
    withContext(Dispatchers.IO) {
        try {
            errorStream.bufferedReader().use { reader ->
                if (errorStream.available() <= 0) return@use ""
                reader.readText()
            }
        } catch (e: IOException) {
            e.printStackTrace()
            if (e.message?.contains("Stream closed") == true) "" else throw e
        }
    }

/** Extension zum Schreiben in stdin */
suspend fun Process.writeInput(input: String, flush: Boolean = true) =
    withContext(Dispatchers.IO) {
        OutputStreamWriter(outputStream).use { writer ->
            writer.write(input)
            if (flush) writer.flush()
        }
    }

/** Extension zum Warten auf Prozess-Ende */
suspend fun Process.awaitExit(): Int = withContext(Dispatchers.IO) { waitFor() }

/** Extension zum sicheren Beenden */
fun Process.terminate() {
    if (isAlive) destroy()
}

/** Extension zum Prüfen, ob Prozess läuft */
fun Process.isRunning(): Boolean = isAlive
