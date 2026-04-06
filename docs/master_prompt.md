Aider Master-Prompt: MobileCodeSpace Professional IDE (Full Project Lifecycle)
Du bist ein Senior Android Software Architekt. Wir bauen "MobileCodeSpace" (MCS), eine hochperformante IDE für Android. Technologie: Kotlin, Jetpack Compose, Multi-Modul-Architektur, Hilt (DI), MVI/MVVM, Flow, Kotlin Serialization. Design: Dark Purple Theme (#2D1B4E), sekundäres Lila (#1A0F2E), Akzentfarbe (#A084DC). Sprache: Deutsche UI-Texte und deutsche Code-Kommentare.
TEIL 1: INFRASTRUKTUR & BASIS-KONFIGURATION
SCHRITT 1: Version Catalog & Modul-Struktur
1. gradle/libs.versions.toml: Initialisiere Versionen für:
   * SoraEditor (Rosemoe), Terminal (termux-view).
   * AndroidX (Compose BOM, Hilt, Navigation, Room, DataStore).
   * Archivierung: commons-compress (1.26.1), xz (1.9).
   * Serialisierung: kotlinx-serialization-json (1.6.3).
   * Plugins: Android Application/Library, Kotlin Android/Serialization, Hilt.
2. settings.gradle.kts: Inkludiere alle 17 Module:
   * :app, :core:ui, :core:data, :core:di, :core:domain, :core:models, :core:navigation, :core:resources, :core:utils
   * :feature:home, :feature:onboarding, :feature:editor, :feature:terminal, :feature:file-tree, :feature:fileexplorer, :feature:git, :feature:settings
SCHRITT 2: Build-System & Projekt-Properties
1. Root build.gradle.kts: Plugins mit apply false definieren (inkl. kotlin-serialization).
2. gradle.properties: Optimale JVM-Args (-Xmx4g), org.gradle.parallel=true, android.nonTransitiveRClass=true.
3. Modul-Builds: Erstelle für jedes Modul eine build.gradle.kts mit eindeutigen Namespaces (com.mcs.<module>).
SCHRITT 3: Globale Konstanten (:app)
Erstelle MCSConstants.kt im :app Modul:
* ROOT_PATH = "MCSProjects"
* WORKSPACE_FILE = "workspace.json"
* PROPS_PATH = ".mcs", EDITOR_PROPS_PATH = "$PROPS_PATH/.editor"
* EDITOR_PROPS_FILE = "open_files.json", EDITOR_PROPS_FILE_BAK = "$EDITOR_PROPS_FILE.bak"
TEIL 2: CORE UTILITIES & SHARED LOGIC
SCHRITT 4: Dateiverwaltung & JSON (:core:utils)
1. FileUtils.kt: Implementiere Funktionen für Dateien und Ordner (create, delete, rename, copy, move, load, save, chmod, listDirectory).
   * Unterstütze createFile(path, isHidden) zur Erstellung versteckter Dateien (mit Punkt-Präfix).
2. ArchiveUtils.kt: Nutze Apache Commons Compress für ZIP, TAR, GZ und 7Z (Entpacken).
3. JsonUtils.kt: Nutze Kotlinx Serialization für toJson, fromJson, saveToFile und loadFromFile.
SCHRITT 5: Ressourcen & UI-System (:core:resources & :core:ui)
1. Ressourcen: Zentralisiere alle res-Ordner in :core:resources. Entferne hartkodierte Texte im gesamten Projekt und nutze String-Ressourcen.
2. Theme: Implementiere MCSTheme (Dark Purple).
3. Komponenten: MCSCard, MCSActionButton, ProjectListItem und eine dynamische Sidepanel-Komponente (horizontaler Menü-Bereich oben, Content-Slot in der Mitte, Tabs unten).
TEIL 3: BACKEND & ONBOARDING
SCHRITT 6: PRoot & Ubuntu Backend (:core:data)
1. PRootManager: Logik zum Deployment der proot-Binary nach filesDir (inkl. chmod +x).
2. Shell-Start: Konfiguration des ProcessBuilder mit Flags -0 -b /sdcard -r <rootfs_path>.
3. PRootService: Ein Background Service zur Verwaltung der Shell-Session.
SCHRITT 7: Onboarding & Download-System (:feature:onboarding)
1. Download-Logik: Implementiere im OnboardingViewModel den Download der cmdline.zip von einer Remote-URL (GitHub Release) statt lokaler Assets.
2. States: Idle, Downloading(progress), Extracting, Completed, Error.
3. Extraktion: Nutze ArchiveUtils.unzip und bereinige temporäre Dateien mit FileUtils.
4. UI: OnboardingScreen mit Fortschrittsanzeige und Fehlerbehandlung.
TEIL 4: IDE FEATURES & EDITOR
SCHRITT 8: Home Screen Dashboard (:feature:home)
1. Dashboard mit Quick Actions ("New Project", "Open", etc.).
2. Liste der "Recent Projects" mit Pfadangabe und Zeitstempel.
SCHRITT 9: SoraEditor & File-Tree
1. Editor: Integration von SoraEditor (Rosemoe) mit Tree-sitter, Textmate und LSP-Support.
2. Sidepanel: Integration des Sidepanels im Editor-Layout (Trigger-Button oben links).
3. File-Tree: Implementiere FileTreeView in :feature:file-tree mit Unterstützung für FileList, PackageList und ModuleList.
4. Projekt-Init: Beim Erstellen eines Projekts müssen die Metadaten-Ordner (.mcs/.editor) und die Dateien (workspace.json, open_files.json) automatisch angelegt werden.
SCHRITT 10: Terminal (:feature:terminal)
1. termux-view Anbindung an den PRoot-Prozess.
2. Sondertasten-Leiste für mobile Nutzung.
TEIL 5: REFACTORING & QUALITÄTSSICHERUNG
SCHRITT 11: Utility-Integration (Refactoring)
Analyse das gesamte Projekt und ersetze alle manuellen Datei-Operationen, ZIP-Logiken oder JSON-Parsings durch die zentralen Klassen in :core:utils. Stelle sicher, dass die Abhängigkeiten in den build.gradle.kts korrekt gesetzt sind.
SCHRITT 12: Gradle Sync & Namespace Check
Überprüfe alle 17 Module auf:
1. Konsistente Namespaces (com.mcs.<module>).
2. Verwendung des Version Catalogs (libs.versions.toml).
3. Korrekte Modul-Abhängigkeiten (Features -> Core).
4. Aktivierung der Kotlin-Serialization in betroffenen Modulen.
SCHRITT 13: Finaler Build-Test
Führe eine AAPT-Validierung durch, behebe Ressourcen-Konflikte und stelle sicher, dass assembleDebug fehlerfrei durchläuft.