package com.mobilecodespace.core.utils

import java.io.File
import java.io.IOException

object FileUtils {

    fun create(path: String): Boolean {
        return try {
            val file = File(path)
            file.parentFile?.mkdirs()
            file.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun createDirectory(path: String): Boolean {
        val dir = File(path)
        return if (!dir.exists()) dir.mkdirs() else true
    }

    fun delete(path: String): Boolean {
        return File(path).deleteRecursively()
    }

    fun rename(path: String, newName: String): Boolean {
        val file = File(path)
        val newFile = File(file.parent, newName)
        return file.renameTo(newFile)
    }

    fun copy(sourcePath: String, destPath: String): Boolean {
        return try {
            val source = File(sourcePath)
            val dest = File(destPath)
            if (source.isDirectory) {
                source.copyRecursively(dest, overwrite = true)
            } else {
                source.copyTo(dest, overwrite = true)
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun move(sourcePath: String, destPath: String): Boolean {
        return try {
            val source = File(sourcePath)
            val dest = File(destPath)
            source.renameTo(dest)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun load(path: String): String? {
        return try {
            File(path).readText()
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun save(path: String, content: String): Boolean {
        return try {
            File(path).writeText(content)
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun chmod(path: String, executable: Boolean): Boolean {
        return File(path).setExecutable(executable)
    }

    fun getExtension(path: String): String {
        return File(path).extension
    }

    fun setExtension(path: String, newExtension: String): String {
        val file = File(path)
        val newPath = file.absolutePath.substringBeforeLast(".") + "." + newExtension
        val newFile = File(newPath)
        return if (file.renameTo(newFile)) newPath else path
    }
}
