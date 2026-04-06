package com.mobilecodespace.core.utils

import java.io.File
import java.io.IOException

object FileUtils {

    fun createDirectory(path: String): Boolean {
        val dir = File(path)
        return if (!dir.exists()) dir.mkdirs() else true
    }

    fun createFile(path: String, content: String = ""): Boolean {
        return try {
            val file = File(path)
            if (!file.exists()) {
                file.parentFile?.mkdirs()
                file.writeText(content)
                true
            } else {
                false
            }
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun deleteRecursively(path: String): Boolean {
        return File(path).deleteRecursively()
    }

    fun rename(path: String, newName: String): Boolean {
        val file = File(path)
        val newFile = File(file.parent, newName)
        return file.renameTo(newFile)
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

    fun copyFile(sourcePath: String, destPath: String): Boolean {
        return try {
            File(sourcePath).copyTo(File(destPath), overwrite = true)
            true
        } catch (e: IOException) {
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

    fun setExecutable(path: String): Boolean {
        return File(path).setExecutable(true)
    }

    fun getExtension(path: String): String {
        return File(path).extension
    }
}
