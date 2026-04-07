package com.mobilecodespace.core.data.deploy

import android.content.Context
import com.mcs.core.utils.Environment
import com.mcs.core.utils.FileUtils
import com.mcs.core.utils.ArchiveUtils
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BinaryDeployer @Inject constructor(private val context: Context) {

    fun deployAll() {
        deployBinaries()
        deployLibs()
        deployAssets()
        deployScripts()
    }

    private fun deployBinaries() {
        val arch = if (FileUtils.isAarch64()) "arm64" else "arm"
        val binaries = listOf("proot")
        
        binaries.forEach { name ->
            val assetPath = "terminal/$arch/$name"
            val destFile = File(Environment.BIN_DIR, name)
            
            try {
                context.assets.open(assetPath).use { input ->
                    FileOutputStream(destFile).use { output ->
                        input.copyTo(output)
                    }
                }
                FileUtils.setFileExecutable(destFile)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun deployLibs() {
        val libs = listOf("liblocal-socket.so", "libtermux.so")
        libs.forEach { name ->
            val assetPath = "libs/$name"
            val destFile = File(Environment.LIB_DIR, name)
            
            try {
                context.assets.open(assetPath).use { input ->
                    FileOutputStream(destFile).use { output ->
                        input.copyTo(output)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun deployAssets() {
        // Kopiert TextMate-Themes und Tree-Sitter-Grammatiken
        val assetPath = "ide_assets.zip"
        val destDir = Environment.MOBILECODESPACE_HOME
        
        try {
            val tempFile = File(context.cacheDir, "ide_assets.zip")
            context.assets.open(assetPath).use { input ->
                FileOutputStream(tempFile).use { output ->
                    input.copyTo(output)
                }
            }
            
            ArchiveUtils.extract(tempFile.absolutePath, destDir.absolutePath)
            tempFile.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun deployScripts() {
        val assetPath = "scripts.zip"
        val destDir = Environment.SCRIPTS
        
        try {
            val tempFile = File(context.cacheDir, "scripts.zip")
            context.assets.open(assetPath).use { input ->
                FileOutputStream(tempFile).use { output ->
                    input.copyTo(output)
                }
            }
            
            ArchiveUtils.extract(tempFile.absolutePath, destDir.absolutePath)
            
            // init.sh ausführbar machen
            val initScript = File(destDir, "init.sh")
            if (initScript.exists()) {
                FileUtils.setFileExecutable(initScript)
            }
            tempFile.delete()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
