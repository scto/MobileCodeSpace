package com.mobilecodespace.core.exec

/**
 * Repräsentiert einen Terminal-Befehl, der in der Sandbox ausgeführt werden soll.
 */
data class TerminalCommand(
    val sandbox: Boolean = true,
    val exe: String,
    val args: Array<String> = arrayOf(),
    val id: String,
    val terminatePreviousSession: Boolean = true,
    val workingDir: String? = null,
    val env: Array<String> = arrayOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TerminalCommand

        if (sandbox != other.sandbox) return false
        if (exe != other.exe) return false
        if (!args.contentEquals(other.args)) return false
        if (id != other.id) return false
        if (terminatePreviousSession != other.terminatePreviousSession) return false
        if (workingDir != other.workingDir) return false
        if (!env.contentEquals(other.env)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = exe.hashCode()
        result = 31 * result + args.contentHashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + workingDir.hashCode()
        result = 31 * result + env.contentHashCode()
        return result
    }
}
