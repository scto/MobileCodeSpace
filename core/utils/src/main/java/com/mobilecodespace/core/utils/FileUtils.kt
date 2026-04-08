package com.mobilecodespace.core.utils

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.webkit.MimeTypeMap
import java.io.*
import java.net.URLDecoder
import java.util.*

/**
 * Zentrale Utility-Klasse für Datei- und Ordneroperationen sowie Bitmap-Manipulation.
 * Optimiert für die Multi-Modul-Architektur der MobileCodeSpace IDE.
 */
object FileUtils {

    // --- ARCHITEKTUR & SYSTEM ---

    /** Prüft, ob das Gerät aarch64 (ARM64) Architektur hat. */
    fun isAarch64(): Boolean {
        return Build.SUPPORTED_ABIS.contains("arm64-v8a")
    }

    /** Setzt das Ausführungsrecht (chmod +x) für eine Datei. */
    fun setFileExecutable(file: File): Boolean {
        return file.setExecutable(true, false)
    }

    // --- DATEI-INFORMATIONEN & PFADE ---

    /** Prüft, ob der Pfad eine existierende Datei ist. */
    fun isFile(path: String): Boolean = File(path).isFile

    /** Gibt die Dateigröße formatiert zurück (z.B. "12.40 kb") */
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

        return String.format(Locale.US, "%.2f", size)
            .replace(".00", "") + " " + units[unitIndex]
    }

    /** Findet den absoluten Pfad einer Datei innerhalb eines Root-Verzeichnisses */
    fun getFilePath(rootDir: String, fileName: String, withExtension: Boolean): String? {
        val directory = File(rootDir)
        if (!directory.exists() || !directory.isDirectory) return null
        
        val targetName = if (fileName.startsWith("/")) fileName.substring(1) else fileName
        val files = directory.listFiles() ?: return null
        
        for (file in files) {
            var path = file.absolutePath
            if (!withExtension && file.isFile) {
                path = path.substringBeforeLast('.')
            }
            
            if (path.endsWith("/$targetName") && file.isFile) {
                return file.absolutePath
            }
            
            if (file.isDirectory) {
                val found = getFilePath(file.absolutePath, fileName, withExtension)
                if (found != null) return found
            }
        }
        return null
    }

    fun getExtension(path: String): String = path.substringAfterLast('.', "")

    fun setExtension(path: String, newExtension: String): String {
        val base = path.substringBeforeLast('.')
        return "$base.$newExtension"
    }

    fun getNameFromAbsolutePath(path: String): String = path.substringAfterLast('/')

    fun getPrefixPath(path: String): String = path.substringBeforeLast('/')

    fun getParentNameFromAbsolutePath(path: String): String = getNameFromAbsolutePath(getPrefixPath(path))

    // --- DATEI-OPERATIONEN ---

    /** Erstellt eine Datei. Falls isHidden true ist, wird ein Punkt vorangestellt. */
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
