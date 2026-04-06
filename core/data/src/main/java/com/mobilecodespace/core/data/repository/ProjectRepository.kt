package com.mobilecodespace.core.data.repository

import com.mobilecodespace.app.MCSConstants
import com.mobilecodespace.core.utils.FileUtils
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectRepository @Inject constructor() {

    /**
     * Initialisiert die Projektstruktur für ein neues Projekt.
     * Erstellt das Hauptverzeichnis, die Metadaten-Ordner und initiale Konfigurationsdateien.
     * @param projectRootPath Der absolute Pfad zum Projekt-Hauptverzeichnis.
     */
    fun initializeProjectStructure(projectRootPath: String): Boolean {
        try {
            val rootDir = File(projectRootPath)
            
            // 1. Hauptverzeichnis erstellen
            if (!FileUtils.createDirectory(projectRootPath)) return false
            if (!rootDir.canWrite()) return false

            // 2. Metadaten-Verzeichnisse erstellen
            // Struktur: projectRoot/MCSProjects/.mcs/.editor
            val mcsBaseDir = File(rootDir, MCSConstants.ROOT_PATH)
            val propsDir = File(mcsBaseDir, MCSConstants.PROPS_PATH)
            val editorPropsDir = File(mcsBaseDir, MCSConstants.EDITOR_PROPS_PATH)

            if (!FileUtils.createDirectory(mcsBaseDir.absolutePath)) return false
            if (!FileUtils.createDirectory(propsDir.absolutePath)) return false
            if (!FileUtils.createDirectory(editorPropsDir.absolutePath)) return false

            // 3. Initial-Dateien erstellen
            // workspace.json im .mcs Ordner
            val workspaceFile = File(propsDir, MCSConstants.WORKSPACE_FILE)
            if (!workspaceFile.exists()) {
                if (!FileUtils.createFile(workspaceFile.absolutePath, "{}")) return false
            }

            // open_files.json im .mcs/.editor Ordner
            val openFilesFile = File(editorPropsDir, MCSConstants.EDITOR_PROPS_FILE)
            if (!openFilesFile.exists()) {
                if (!FileUtils.createFile(openFilesFile.absolutePath, "[]")) return false
            }

            // open_files.json.bak im .mcs/.editor Ordner
            val openFilesBakFile = File(editorPropsDir, MCSConstants.EDITOR_PROPS_FILE_BAK)
            if (!openFilesBakFile.exists()) {
                if (!FileUtils.createFile(openFilesBakFile.absolutePath, "[]")) return false
            }

            // 4. Validierung: Setze Ausführungsrechte für das Metadaten-Verzeichnis
            // Dies ist wichtig für PRoot, damit es auf die Metadaten zugreifen kann
            FileUtils.setExecutable(mcsBaseDir.absolutePath)

            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }
}
