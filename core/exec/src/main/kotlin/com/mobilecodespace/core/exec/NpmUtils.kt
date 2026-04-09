package com.mobilecodespace.core.exec

import org.json.JSONObject

object NpmUtils {
    /** Ruft die installierte Version ab. */
    suspend fun getInstalledVersion(packageName: String): String? {
        val result =
            ShellUtils.runUbuntu(
                command = arrayOf("npm", "list", "-g", "--prefix", "/usr", packageName, "--depth=0", "--json"),
                timeoutSeconds = 5L,
            )
        if (result.timedOut || result.exitCode != 0) return null

        return runCatching {
                val obj = JSONObject(result.output)
                obj.getJSONObject("dependencies").getJSONObject(packageName).getString("version")
            }
            .getOrNull()
    }

    /** Ruft die neueste Version ab. */
    suspend fun getLatestVersion(packageName: String): String? {
        val result =
            ShellUtils.runUbuntu(command = arrayOf("npm", "view", packageName, "version"), timeoutSeconds = 20L)
        if (result.timedOut || result.exitCode != 0) return null
        return result.output
    }

    /** Prüft, ob ein Update verfügbar ist. */
    suspend fun hasUpdate(packageName: String): Boolean {
        val currentVersion = getInstalledVersion(packageName) ?: return false
        val latestVersion = getLatestVersion(packageName) ?: return false
        return currentVersion != latestVersion
    }
}
