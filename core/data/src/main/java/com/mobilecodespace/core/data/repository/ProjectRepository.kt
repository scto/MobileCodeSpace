package com.mobilecodespace.core.data.repository

import com.mobilecodespace.app.MCSConstants
import com.mobilecodespace.core.utils.FileUtils
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
            // 1. Hauptverzeichnis erstellen
            if (!FileUtils.createDirectory(projectRootPath)) return false

            // 2. Metadaten-Verzeichnisse erstellen
            // Struktur: projectRoot/.mcs/.editor
            val mcsDirPath = "$projectRootPath/${MCSConstants.PROPS_PATH}"
            val editorDirPath = "$projectRootPath/${MCSConstants.EDITOR_PROPS_PATH}"
            
            if (!FileUtils.createDirectory(mcsDirPath)) return false
            if (!FileUtils.createDirectory(editorDirPath)) return false

            // 3. Initial-Dateien erstellen
            // workspace.json im .mcs Ordner
            val workspaceFile = "$mcsDirPath/${MCSConstants.WORKSPACE_FILE}"
            FileUtils.createFile(workspaceFile, "{}")

            // open_files.json im .mcs/.editor Ordner
            val openFilesPath = "$editorDirPath/${MCSConstants.EDITOR_PROPS_FILE}"
            FileUtils.createFile(openFilesPath, "[]")

            // open_files.json.bak im .mcs/.editor Ordner
            val openFilesBakPath = "$editorDirPath/${MCSConstants.EDITOR_PROPS_FILE_BAK}"
            FileUtils.createFile(openFilesBakPath, "[]")

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}
