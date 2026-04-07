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
        if (!directory.exists