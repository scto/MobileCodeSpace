Aider Master-Prompt: MobileCodeSpace Professional IDE (Build-Ready v2.4)

Du bist ein Senior Android Software Architekt. Wir bauen "MobileCodeSpace" (MCS), eine hochperformante IDE für Android. Technologie: Kotlin, Jetpack Compose, Multi-Modul-Architektur, Hilt (DI), MVI/MVVM, Flow. Design: Dark Purple Theme (#2D1B4E) gemäß Referenzbildern. Sprache: Deutsche UI-Texte und deutsche Code-Kommentare.

SCHRITT 1: Version Catalog & Projekt-Infrastruktur (Root Setup)

1. gradle/libs.versions.toml: SoraEditor (Rosemoe), Terminal (Termux-View), AndroidX Compose, Hilt, Room, DataStore.

2. Root build.gradle.kts: Plugins mit apply false.

3. settings.gradle.kts: Inkludiere alle 17 Module (inkl. :core:utils, :core:resources, :feature:file-tree).

4. gradle.properties: JVM-Args (-Xmx4g), parallel=true.

SCHRITT 2: Submodul Build-Konfiguration

Erstelle für jedes Modul eine individuelle build.gradle.kts mit korrekten Namespaces (com.mcs.*) und Abhängigkeiten.

SCHRITT 3: App-Zentrale & Lifecycle (:app)

1. MCSApplication: Hilt-Initialisierung (@HiltAndroidApp).

2. MCSActivity: Hostet den zentralen NavHost.

3. Manifest: Permissions (MANAGE_EXTERNAL_STORAGE), Provider und Services registrieren.

SCHRITT 4: Shared UI & Design System (:core:ui)

1. Theme: MCSTheme (Primary: #2D1B4E, Accent: #A084DC).

2. Komponenten: MCSCard, MCSActionButton, ProjectListItem.

SCHRITT 5: PRoot Ubuntu Backend (:core:data)

1. PRootManager: Binary-Handling, Rootfs-Extraktion und Shell-Start (-0 -b /sdcard -r <path>).

2. PRootService: Hintergrund-Service für die Shell-Session.

SCHRITT 6: SoraEditor Integration (:feature:editor)

1. EditorActivity: SoraEditor (Rosemoe) Integration mit Tree-sitter, Textmate und LSP.

2. Features: Save, Undo/Redo, Cut/Copy/Paste, Format.

SCHRITT 7: Home Dashboard & Onboarding

1. Onboarding: Setup-Flow für Tools.

2. Home Screen: Dashboard mit Quick Actions und Recent Projects.

SCHRITT 8: Terminal (:feature:terminal)

1. TerminalActivity: termux-view Anbindung an PRoot-Streams.

SCHRITT 9: Build-Stabilität & AAPT-Validierung

Vermeidung von Ressourcen-Konflikten und Sicherstellung der Kompilierbarkeit.

SCHRITT 10: Globale Konstanten (:app)

Erstelle im Modul :app die Datei MCSConstants.kt:

object MCSConstants {

   const val ROOT_PATH = "MCSProjects"

   const val WORKSPACE_FILE = "workspace.json"

   const val PROPS_PATH = ".mcs"

   const val EDITOR_PROPS_PATH = "$PROPS_PATH/.editor"

   const val EDITOR_PROPS_FILE = "$EDITOR_PROPS_PATH/open_files.json"

   const val EDITOR_PROPS_FILE_BAK = "$EDITOR_PROPS_FILE.bak"

}



SCHRITT 11: Utility Modul für Dateiverwaltung (:core:utils)

Erstelle das Modul :core:utils und implementiere FileUtils.kt:

* Dateien: create, delete, rename, copy, move, load, save, chmod, getExtension, setExtension.

* Ordner: Entsprechende Funktionen für Verzeichnis-Operationen (rekursives Löschen/Kopieren).

* Nutze moderne Kotlin-IO oder java.nio.file.

SCHRITT 12: Ressourcen-Zentralisierung (:core:resources)

1. Zentralisierung: Verschiebe alle Projekt-weiten res-Ordner (Strings, Drawables, Colors) in :core:resources.

2. String-Cleanup: Durchsuche das gesamte Projekt nach hartkodierten Texten, erstelle dafür passende Einträge in strings.xml (Deutsch) und ersetze die Vorkommen im Code durch Ressourcen-Aufrufe.

SCHRITT 13: Sidepanel Komponente (:core:ui)

Erstelle in :core:ui/components die Komponente Sidepanel:

* Oberer Bereich: Horizontal angeordnete Menü-Einträge (Icon-Buttons, Text-Buttons, Listen).

* Hauptbereich (Content): Dynamischer Slot für Views (z.B. FileTreeView).

* Unterer Bereich: Dynamische Tabs basierend auf der Anzahl der verfügbaren Views (Text-Tabs, beim Anklicken wird der View gewechselt).

SCHRITT 14: File-Tree System (:feature:file-tree)

1. Models: Implementiere FileNode, FileNodeList und Attribute.

2. Konfiguration:

   * showHidden: Boolean.

   * sortOrder: Enum (Datum, Größe, Typ, Name).

   * listType: Enum (FileList, PackageList, ModuleList) zur Darstellung wie in Android Studio.

3. Logik: Der FileTreeView erfordert zwingend einen Pfad als Parameter, der vom Editor-Modul (geöffnetes Projekt) übergeben wird.

SCHRITT 15: Editor UI Enhancements (:feature:editor)

Erweitere die EditorActivity / den EditorScreen:

1. Sidepanel-Trigger: Ein Button oben links, der das Sidepanel öffnet/schließt (ähnlich einem Navigation Drawer).

2. Top-Right Action Menu: Ein 3-Punkte-Menü mit:

   * Open, Close, Close All.

   * Save, Save All.

   * Tools (Submenü): Search in File, Search in Project, Format.




