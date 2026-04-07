package com.mobilecodespace.core.exec

import org.json.JSONObject

/**
 * Hilfsklasse für NPM-Operationen innerhalb der Sandbox.
 */
object NpmUtils {
    suspend fun getInstalledVersion(packageName: String): String? {
        val result =
            ShellUtils.runUbuntu(
                command = arrayOf("npm", "list", "-g", "--prefix", "/usr", packageName, "--depth=0", "--json"),
                timeoutSeconds = 5L,
            )
        if (result.timedOut || result.exitCode != 0)