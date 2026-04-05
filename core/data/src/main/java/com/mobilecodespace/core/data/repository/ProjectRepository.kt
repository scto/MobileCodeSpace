package com.mobilecodespace.core.data.repository

import com.mobilecodespace.app.MCSConstants
import com.mobilecodespace.core.utils.FileUtils
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectRepository @Inject constructor() {

    /**
     * Initialisiert die Ordnerstruktur für ein neues Projekt.
     * @param projectRootPath Der absolute Pfad zum Projekt-Hauptverzeichnis.
     */
    fun initializeProjectStructure(projectRootPath: String): Boolean {
        try {
            // 1. Hauptverzeichnis erstellen und auf Schreibrechte prüfen
            val rootDir = File(projectRootPath)
            if (!rootDir.exists() && !rootDir.mkdirs()) return false
            if (!rootDir.canWrite()) return false

            // 2. Metadaten-Verzeichnisse erstellen
            // Struktur: projectRoot/MCSProjects/.mcs/.editor
            val internalDataDir = File(projectRootPath, MCSConstants.ROOT_PATH)
            val mcsDirPath = File(internalDataDir, MCSConstants.PROPS_PATH)
            val editorDirPath = File(internalDataDir, MCSConstants.EDITOR_PROPS_PATH)
            
            if (!FileUtils.createDirectory(mcsDirPath.absolutePath)) return false
            if (!FileUtils.createDirectory(editorDirPath.absolutePath)) return false

            // 3. Initial-Dateien erstellen
            // workspace.json im .mcs Ordner
            val workspaceFile = File(mcsDirPath, MCSConstants.WORKSPACE_FILE)
            if (!FileUtils.createFile(workspaceFile.absolutePath, "{}")) return false

            // open_files.json im .mcs/.editor Ordner
            val openFilesPath = File(editorDirPath, MCSConstants.EDITOR_PROPS_FILE)
            if (!FileUtils.createFile(openFilesPath.absolutePath, "[]")) return false

            // open_files.json.bak im .mcs/.editor Ordner
            val openFilesBakPath = File(editorDirPath, MCSConstants.EDITOR_PROPS_FILE_BAK)
            if (!FileUtils.createFile(openFilesBakPath.absolutePath, "[]")) return false

            // 4. Validierung: Setze Ausführungsrechte für das Metadaten-Verzeichnis
            // Dies ist wichtig für PRoot, damit es auf die Metadaten zugreifen kann
            FileUtils.setExecutable(mcsDirPath.absolutePath)

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}
