package com.mcs.core.utils

import android.os.Build
import java.io.File
import java.io.IOException
import java.util.Locale

object FileUtils {
    fun isAarch64(): Boolean = Build.SUPPORTED_ABIS.contains("arm64-v8a")
    
    fun setFileExecutable(file: File): Boolean = file.setExecutable(true, false)
    
    fun isFile(path: String): Boolean = File(path).isFile
    
    fun getFileSize(filePath: String): String? {
        if (!isFile(filePath)) return null
        val file = File(filePath)
        var size = file.length().toDouble()
        val units = arrayOf("byte", "kb", "mb", "gb")
        var unitIndex = 0
        while (size > 1024 && unitIndex < units.size - 1) {
            size /= 1024
            unitIndex++
        }
        return String.format(Locale.US, "%.2f", size).replace(".00", "") + " " + units[unitIndex]
    }
    
    fun createFile(path: String, isHidden: Boolean = false): Boolean {
        val file = File(path)
        if (file.exists()) return true
        return try {
            file.parentFile?.mkdirs()
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun copyRecursively(source: File, target: File): Boolean {
        return try {
            source.copyRecursively(target, overwrite = true)
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun deleteRecursively(file: File): Boolean {
        return file.deleteRecursively()
    }
}
