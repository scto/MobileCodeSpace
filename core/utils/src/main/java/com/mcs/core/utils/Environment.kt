package com.mcs.core.utils

import android.content.Context
import com.mobilecodespace.app.MCSConstants
import java.io.File

object Environment {
    private lateinit var appFilesDir: File
    
    fun init(context: Context) { 
        appFilesDir = context.filesDir 
    }
    
    val ROOT_DIR: File get() = appFilesDir
    val MOBILECODESPACE_HOME: File get() = File(appFilesDir, MCSConstants.MOBILECODESPACE_HOME)
    val ROOTFS: File get() = File(MOBILECODESPACE_HOME, "rootfs")
    val HOME: File get() = File(MOBILECODESPACE_HOME, "home")
    val SCRIPTS: File get() = File(MOBILECODESPACE_HOME, "scripts")
    val PROOT: File get() = File(MOBILECODESPACE_HOME, "proot")
    val BIN_DIR: File get() = File(MOBILECODESPACE_HOME, "bin")
    val LIB_DIR: File get() = File(MOBILECODESPACE_HOME, "lib")

    fun ensureDirectories() {
        MOBILECODESPACE_HOME.mkdirs()
        ROOTFS.mkdirs()
        HOME.mkdirs()
        SCRIPTS.mkdirs()
        PROOT.mkdirs()
        BIN_DIR.mkdirs()
        LIB_DIR.mkdirs()
    }
}
