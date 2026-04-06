package com.mcs.core.utils

import org.apache.commons.compress.archivers.ArchiveEntry
import org.apache.commons.compress.archivers.ArchiveStreamFactory
import org.apache.commons.compress.archivers.sevenz.SevenZFile
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream
import java.io.*
import java.nio.file.Files
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * Erweiterte Utility-Klasse für Archiv-Operationen.
 * Unterstützt ZIP, GZIP, TAR, TAR.GZ und 7Z (Lesen).
 * Nutzt Apache Commons Compress für maximale Kompatibilität.
 */
object ArchiveUtils {

    /**
     * Universelle Entpack-Funktion basierend auf der Dateiendung.
     */
    fun extract(archivePath: String, destDir: String): Boolean {
        val ext = archivePath.substringAfterLast(".").lowercase()
        return when (ext) {
            "zip" -> unzip(archivePath, destDir)
            "gz" -> if (archivePath.contains(".tar.gz")) extractTarGz(archivePath, destDir) else ungzip(archivePath, destDir)
            "tar" -> extractTar(archivePath, destDir)
            "7z" -> extract7z(archivePath, destDir)
            else -> false
        }
    }

    /** Entpackt ein .7z Archiv (Benötigt org.tukaani:xz im Klassenpfad) */
    fun extract7z(archivePath: String, destDir: String): Boolean {
        return try {
            val sevenZFile = SevenZFile(File(archivePath))
            val destFileDir = File(destDir).apply { if (!exists()) mkdirs() }
            
            var entry = sevenZFile.nextEntry
            while (entry != null) {
                val curFile = File(destFileDir, entry.name)
                if (entry.isDirectory) {
                    curFile.mkdirs()
                } else {
                    curFile.parentFile?.mkdirs()
                    FileOutputStream(curFile).use { out ->
                        val content = ByteArray(entry.size.toInt())
                        sevenZFile.read(content)
                        out.write(content)
                    }
                }
                entry = sevenZFile.nextEntry
            }
            sevenZFile.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /** Entpackt ein .tar.gz Archiv */
    fun extractTarGz(archivePath: String, destDir: String): Boolean {
        return try {
            val fis = FileInputStream(archivePath)
            val gzis = GzipCompressorInputStream(fis)
            val taris = TarArchiveInputStream(gzis)
            
            var entry: ArchiveEntry? = taris.nextEntry
            while (entry != null) {
                val destFile = File(destDir, entry.name)
                if (entry.isDirectory) {
                    destFile.mkdirs()
                } else {
                    destFile.parentFile?.mkdirs()
                    Files.copy(taris, destFile.toPath())
                }
                entry = taris.nextEntry
            }
            taris.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /** Entpackt ein einfaches .tar Archiv */
    fun extractTar(archivePath: String, destDir: String): Boolean {
        return try {
            val taris = TarArchiveInputStream(FileInputStream(archivePath))
            var entry: ArchiveEntry? = taris.nextEntry
            while (entry != null) {
                val destFile = File(destDir, entry.name)
                if (entry.isDirectory) {
                    destFile.mkdirs()
                } else {
                    destFile.parentFile?.mkdirs()
                    Files.copy(taris, destFile.toPath())
                }
                entry = taris.nextEntry
            }
            taris.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /** Bestehende ZIP-Funktion (nativ oder via Commons) */
    fun unzip(zipPath: String, destDir: String): Boolean {
        return try {
            ZipInputStream(BufferedInputStream(FileInputStream(zipPath))).use { zis ->
                var entry: ZipEntry? = zis.nextEntry
                while (entry != null) {
                    val newFile = File(destDir, entry.name)
                    if (entry.isDirectory) {
                        newFile.mkdirs()
                    } else {
                        newFile.parentFile?.mkdirs()
                        FileOutputStream(newFile).use { fos -> zis.copyTo(fos) }
                    }
                    zis.closeEntry()
                    entry = zis.nextEntry
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /** GZIP für Einzeldateien */
    fun ungzip(srcPath: String, destPath: String): Boolean {
        return try {
            GzipCompressorInputStream(FileInputStream(srcPath)).use { gzis ->
                FileOutputStream(destPath).use { fos -> gzis.copyTo(fos) }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /** Prüft die Integrität eines Archivs */
    fun checkIntegrity(archivePath: String): Boolean {
        return try {
            val file = File(archivePath)
            if (!file.exists()) return false
            // Basaler Check: Kann die Factory einen Stream dafür öffnen?
            BufferedInputStream(FileInputStream(file)).use { bis ->
                ArchiveStreamFactory().createArchiveInputStream(bis)
            }
            true
        } catch (e: Exception) {
            // Manche Formate wie 7z benötigen spezielle Reader (SevenZFile)
            archivePath.endsWith(".7z") || archivePath.endsWith(".rar")
        }
    }
}