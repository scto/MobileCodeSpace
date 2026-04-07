package com.mcs.core.utils

import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import java.io.*
import java.util.zip.ZipInputStream

object ArchiveUtils {
    fun extract(archivePath: String, destDir: String): Boolean {
        val file = File(archivePath)
        if (!file.exists()) return false
        val ext = archivePath.substringAfterLast(".").lowercase()
        return when (ext) {
            "zip" -> unzip(archivePath, destDir)
            "gz" -> if (archivePath.contains(".tar.gz")) extractTarGz(archivePath, destDir) else false
            "tar" -> extractTar(archivePath, destDir)
            else -> false
        }
    }
    private fun unzip(zipPath: String, destDir: String): Boolean {
        return try {
            ZipInputStream(BufferedInputStream(FileInputStream(zipPath))).use { zis ->
                var entry = zis.nextEntry
                while (entry != null) {
                    val newFile = File(destDir, entry.name)
                    if (entry.isDirectory) {
                        newFile.mkdirs()
                    } else {
                        newFile.parentFile?.mkdirs()
                        FileOutputStream(newFile).use { fos -> zis.copyTo(fos) }
                    }
                    entry = zis.nextEntry
                }
            }
            true
        } catch (e: Exception) { e.printStackTrace(); false }
    }
    private fun extractTarGz(archivePath: String, destDir: String): Boolean {
        return try {
            FileInputStream(archivePath).use { fis ->
                GzipCompressorInputStream(fis).use { gzis ->
                    TarArchiveInputStream(gzis).use { taris ->
                        var entry = taris.nextEntry
                        while (entry != null) {
                            val destFile = File(destDir, entry.name)
                            if (entry.isDirectory) {
                                destFile.mkdirs()
                            } else {
                                destFile.parentFile?.mkdirs()
                                FileOutputStream(destFile).use { fos -> taris.copyTo(fos) }
                            }
                            entry = taris.nextEntry
                        }
                    }
                }
            }
            true
        } catch (e: Exception) { e.printStackTrace(); false }
    }
    private fun extractTar(archivePath: String, destDir: String): Boolean {
        return try {
            TarArchiveInputStream(FileInputStream(archivePath)).use { taris ->
                var entry = taris.nextEntry
                while (entry != null) {
                    val destFile = File(destDir, entry.name)
                    if (entry.isDirectory) {
                        destFile.mkdirs()
                    } else {
                        destFile.parentFile?.mkdirs()
                        FileOutputStream(destFile).use { fos -> taris.copyTo(fos) }
                    }
                    entry = taris.nextEntry
                }
            }
            true
        } catch (e: Exception) { e.printStackTrace(); false }
    }
}
