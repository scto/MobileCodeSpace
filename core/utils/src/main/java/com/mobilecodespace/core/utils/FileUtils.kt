package com.mcs.core.utils

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.webkit.MimeTypeMap
import androidx.activity.result.ActivityResultLauncher
import java.io.*
import java.net.URLDecoder
import java.util.*

/**
 * Zentrale Utility-Klasse für Datei- und Ordneroperationen sowie Bitmap-Manipulation.
 * Optimiert für die Multi-Modul-Architektur der MobileCodeSpace IDE.
 */
object FileUtils {

    // --- DATEI-INFORMATIONEN & PFADE ---

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
        var finalPath = path
        if (isHidden) {
            val file = File(path)
            if (!file.name.startsWith(".")) {
                finalPath = "${file.parent}${File.separator}.${file.name}"
            }
        }
        
        val file = File(finalPath)
        if (file.exists()) return true
        
        file.parentFile?.mkdirs()
        return try {
            file.createNewFile()
        } catch (e: IOException) {
            false
        }
    }

    /** Erstellt ein Verzeichnis rekursiv */
    fun createDirectory(path: String): Boolean {
        val file = File(path)
        return if (file.exists()) file.isDirectory else file.mkdirs()
    }

    fun rename(path: String, newName: String): Boolean {
        val file = File(path)
        val newFile = File("${getPrefixPath(path)}/$newName")
        return file.renameTo(newFile)
    }

    fun deleteFile(path: String): Boolean {
        val file = File(path)
        return if (file.exists() && file.isFile) file.delete() else false
    }

    fun deleteDirectory(path: String): Boolean {
        val file = File(path)
        return if (file.exists() && file.isDirectory) file.deleteRecursively() else false
    }

    /** Setzt Linux-Dateirechte (Wichtig für PRoot/Ubuntu) */
    fun chmod(path: String, mode: String): Boolean {
        return try {
            Runtime.getRuntime().exec("chmod $mode $path").waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }

    // --- KOPIEREN & VERSCHIEBEN ---
    @Throws(IOException::class)
    fun copyFile(srcPath: String, destPath: String) {
        val src = File(srcPath)
        if (src.isFile) src.copyTo(File(destPath), overwrite = true)
    }

    @Throws(IOException::class)
    fun copyDirectory(srcPath: String, destPath: String) {
        val src = File(srcPath)
        if (src.isDirectory) src.copyRecursively(File(destPath), overwrite = true)
    }

    @Throws(IOException::class)
    fun moveFile(srcPath: String, destPath: String) {
        copyFile(srcPath, destPath)
        deleteFile(srcPath)
    }

    @Throws(IOException::class)
    fun moveDirectory(srcPath: String, destPath: String) {
        copyDirectory(srcPath, destPath)
        deleteDirectory(srcPath)
    }

    // --- LISTING ---
    /** Listet alle Pfade in einem Verzeichnis auf (für den FileTree) */
    fun listDirectory(path: String): List<String> {
        val dir = File(path)
        if (!dir.exists() || !dir.isDirectory) return emptyList()
        return dir.listFiles()?.map { it.absolutePath }?.sorted() ?: emptyList()
    }

    fun isDirectory(path: String) = File(path).isDirectory
    fun isFile(path: String) = File(path).isFile

    // --- LESEN & SCHREIBEN ---
    fun load(path: String): String = try { File(path).readText(Charsets.UTF_8) } catch (e: Exception) { "" }

    fun save(path: String, content: String) {
        try {
            val file = File(path)
            if (!file.exists()) createFile(path)
            file.writeText(content, Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // --- ASSETS & RESSOURCEN ---
    fun copyFromAssets(context: Context, assetPath: String, destPath: String) {
        val destFile = File(destPath)
        destFile.parentFile?.mkdirs()
        try {
            context.assets.open(assetPath).use { input ->
                FileOutputStream(destFile).use { output ->
                    input.copyTo(output)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun readFileFromAssets(context: Context, path: String): String {
        return try {
            context.assets.open(path).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            ""
        }
    }

    // --- BITMAP MANIPULATION ---
    fun saveBitmap(bitmap: Bitmap, destPath: String) {
        createFile(destPath)
        try {
            FileOutputStream(File(destPath)).use { out ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getScaledBitmap(path: String, max: Int): Bitmap {
        val src = BitmapFactory.decodeFile(path)
        val width = src.width
        val height = src.height
        val rate: Float = if (width > height) max.toFloat() / width else max.toFloat() / height
        return Bitmap.createScaledBitmap(src, (width * rate).toInt(), (height * rate).toInt(), true)
    }

    fun makeCircleBitmap(src: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint().apply { isAntiAlias = true }
        val rect = Rect(0, 0, src.width, src.height)
        canvas.drawCircle(src.width / 2f, src.height / 2f, src.width / 2f, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(src, rect, rect, paint)
        return output
    }

    fun setBitmapBrightness(src: Bitmap, brightness: Float): Bitmap {
        val cm = ColorMatrix(floatArrayOf(
            1f, 0f, 0f, 0f, brightness,
            0f, 1f, 0f, 0f, brightness,
            0f, 0f, 1f, 0f, brightness,
            0f, 0f, 0f, 1f, 0f
        ))
        val output = Bitmap.createBitmap(src.width, src.height, src.config)
        val canvas = Canvas(output)
        val paint = Paint().apply { colorFilter = ColorMatrixColorFilter(cm) }
        canvas.drawBitmap(src, 0f, 0f, paint)
        return output
    }

    // --- URI HANDLING ---
    fun convertUriToFilePath(context: Context, uri: Uri): String? {
        var path: String? = null
        if (DocumentsContract.isDocumentUri(context, uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            if (isExternalStorageDocument(uri)) {
                val split = docId.split(":")
                if ("primary".equals(split[0], ignoreCase = true)) {
                    path = "${Environment.getExternalStorageDirectory()}/${split[1]}"
                }
            } else if (isDownloadsDocument(uri)) {
                if (!TextUtils.isEmpty(docId) && docId.startsWith("raw:")) {
                    return docId.replaceFirst("raw:", "")
                }
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse("content://downloads/public_downloads"),
                    docId.toLong()
                )
                path = getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val split = docId.split(":")
                val type = split[0]
                val contentUri = when (type) {
                    "image" -> MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    "video" -> MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    "audio" -> MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    else -> null
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])
                path = contentUri?.let { getDataColumn(context, it, selection, selectionArgs) }
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            path = getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            path = uri.path
        }

        return path?.let {
            try {
                URLDecoder.decode(it, "UTF-8")
            } catch (e: Exception) {
                null
            }
        }
    }

    private fun getDataColumn(context: Context, uri: Uri, selection: String?, selectionArgs: Array<String>?): String? {
        val column = "_data"
        val projection = arrayOf(column)
        context.contentResolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        }
        return null
    }

    private fun isExternalStorageDocument(uri: Uri) = "com.android.externalstorage.documents" == uri.authority
    private fun isDownloadsDocument(uri: Uri) = "com.android.providers.downloads.documents" == uri.authority
    private fun isMediaDocument(uri: Uri) = "com.android.providers.media.documents" == uri.authority

    // --- INTENTS ---
    fun openFileWithOtherApp(context: Context, path: String) {
        val file = File(path)
        val map = MimeTypeMap.getSingleton()
        val ext = MimeTypeMap.getFileExtensionFromUrl(file.name)
        val type = map.getMimeTypeFromExtension(ext) ?: "*/*"
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(Uri.fromFile(file), type)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }

    // --- DATEI-TYPEN & KATEGORIEN ---
    object FileItem {
        const val VIDEO = "video"
        const val CODE = "code"
        const val AUDIO = "audio"
        const val ARCHIVE = "archive"
        const val ANDROID = "android"
        const val PIC = "picture"
        const val DOC = "doc"

        private const val EXT_TEXT = ",txt,xml,conf,prop,cpp,h,java,class,log,json,js,php,css,py,c,cfg,ini,bat,mf,mtd,lua,html,htm,kt,gradle,pro,properties,"
        private const val EXT_VIDEO = ",3gp,asf,avi,mp4,mpe,mpeg,mpg,mpg4,m4u,m4v,mov,rmvb,"
        private const val EXT_DOC = ",docx,doc,md,xls,xlsx,ppt,pptx,pdf,"
        private const val EXT_AUDIO = ",m3u,m4a,m4b,m4p,mp2,mp3,mpga,ogg,wav,wma,wmv,3gpp,flac,amr,"
        private const val EXT_PIC = ",jpg,jpeg,png,gif,webp,exif,tiff,bmp,psd,svg,"
        private const val EXT_ANDROID = ",apk,aab,"
        private const val EXT_ARCHIVE = ",zip,rar,7z,tar,jar,gz,xz,"

        fun getType(path: String): String {
            val ext = ",${path.substringAfterLast('.', "").lowercase()},"
            return when {
                EXT_PIC.contains(ext) -> PIC
                EXT_VIDEO.contains(ext) -> VIDEO
                EXT_TEXT.contains(ext) -> CODE
                EXT_DOC.contains(ext) -> DOC
                EXT_AUDIO.contains(ext) -> AUDIO
                EXT_ARCHIVE.contains(ext) -> ARCHIVE
                EXT_ANDROID.contains(ext) -> ANDROID
                else -> CODE
            }
        }
    }
}