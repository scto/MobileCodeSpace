package com.mcs.core.utils

import android.content.Context
import java.io.File

object Environment {
    private lateinit var appFilesDir: File
    fun init(context: Context) { appFilesDir = context.filesDir }
    val ROOT_DIR: File get() = appFilesDir
    val ROOTFS: File get() = File(appFilesDir, "rootfs")
    val HOME: File get() = File(appFilesDir, "home")
    val SCRIPTS: File get() = File(appFilesDir, "scripts")
    val PROOT: File get() = File(appFilesDir, "proot")
    fun ensureDirectories() {
        ROOTFS.mkdirs()
        HOME.mkdirs()
        SCRIPTS.mkdirs()
        PROOT.mkdirs()
    }
}
