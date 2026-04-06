Aider Master-Prompt: MobileCodeSpace Professional IDE (Final Version)
Du bist ein Senior Android Software Architekt. Wir bauen "MobileCodeSpace" (MCS), eine hochperformante IDE für Android. Technologie: Kotlin, Jetpack Compose, Multi-Modul-Architektur, Hilt (DI), MVI/MVVM, Flow, Kotlin Serialization. Design: Dark Purple Theme (#2D1B4E), sekundäres Lila (#1A0F2E), Akzentfarbe (#A084DC). Sprache: Deutsche UI-Texte und deutsche Code-Kommentare.
TEIL 1: PROJEKT-INFRASTRUKTUR
SCHRITT 1: Version Catalog & Modul-Struktur
1. gradle/libs.versions.toml: Initialisiere alle Versionen (SoraEditor, Termux-View, Compose, Hilt, Room, DataStore).
   * Archivierung: commons-compress (1.26.1), xz (1.9).
   * JSON: kotlinx-serialization-json (1.6.3).
2. settings.gradle.kts: Inkludiere alle 17 Module (inkl. :core:utils, :core:resources, :feature:file-tree, etc.).
SCHRITT 2: Build-Konfiguration & Namespaces
* Erstelle für jedes Modul eine build.gradle.kts.
* Zwingend: Jedes Modul benötigt einen eindeutigen namespace (com.mcs.<modulname>), um AAPT-Fehler zu vermeiden.
* Nutze ausschließlich den Version Catalog.
SCHRITT 3: App-Zentrale & Konstanten (:app)
1. MCSApplication: Hilt-Initialisierung (@HiltAndroidApp).
2. MCSConstants.kt: Definiere Pfade für ROOT_PATH ("MCSProjects"), WORKSPACE_FILE, .mcs (Props) und .editor (Open Files).
TEIL 2: CORE UTILITIES & DATEIVERWALTUNG
SCHRITT 4: File & Json Utilities (:core:utils)
1. FileUtils.kt: Implementiere Funktionen für Dateien/Ordner (create, delete, rename, copy, move, load, save, chmod, listDirectory). Unterstütze versteckte Dateien (Punkt-Präfix).
2. JsonUtils.kt: Nutze Kotlinx Serialization für toJson, fromJson und Datei-Persistenz.
SCHRITT 5: Architektur-Check & Binary Deployment (NEU)
ArchUtils.kt in :core:utils:
* Implementiere Logik zur Erkennung der Geräte-Architektur (arm vs. aarch64) mittels android.os.Build.SUPPORTED_ABIS.
* Funktion zum Kopieren der passenden proot-Binary aus assets/arm/proot oder assets/aarch64/proot in das filesDir der App inklusive chmod +x.
SCHRITT 6: Archivierung & Scripting (NEU)
1. ArchiveUtils.kt: Support für ZIP, TAR, GZ und 7Z mittels Apache Commons Compress.
2. Script Deployment: Implementiere Logik in :core:data, um den Inhalt von assets/scripts in den internen Speicher zu entpacken und die Datei init.sh auszuführen (via ProcessBuilder), um die Umgebung vorzubereiten.
TEIL 3: UI-SYSTEM & FEATURES
SCHRITT 7: Shared UI & Sidepanel (:core:ui)
1. MCSTheme: Dark Purple Design.
2. Sidepanel-Komponente: Oberes Menü (Icons/Buttons), Hauptbereich (Slot für Content), untere Tab-Leiste für dynamische View-Wechsel.
SCHRITT 8: Onboarding mit Cloud-Download (:feature:onboarding)
* Logik: Lade cmdline.zip von einer Remote-URL herunter (GitHub Release).
* States: Idle, Downloading (mit Prozent-Fortschritt), Extracting, Completed, Error.
* Nutze ArchiveUtils.unzip und lösche das Archiv nach Erfolg mit FileUtils.
* Fordere MANAGE_EXTERNAL_STORAGE an.
SCHRITT 9: Home Dashboard & Projekt-Logik
* HomeScreen: Dashboard für Quick Actions und Recent Projects.
* Projekt-Initialisierung: Beim Erstellen eines Projekts automatisch den .mcs-Ordner und die workspace.json anlegen.
TEIL 4: EDITOR & TERMINAL
SCHRITT 10: SoraEditor Integration (:feature:editor)
* Integration von Rosemoe's SoraEditor mit Tree-sitter und TextMate-Theming.
* Top-Action-Bar: Save, Undo/Redo, Format.
* Sidepanel-Integration (Trigger-Button oben links).
SCHRITT 11: File-Tree System (:feature:file-tree)
* FileTreeView mit Unterstützung für FileList, PackageList und ModuleList.
* Filtern von versteckten Dateien und Sortierung (Datum, Größe, Typ).
SCHRITT 12: Terminal & PRoot Shell (:feature:terminal)
* Anbindung der termux-view an die PRoot-Ubuntu-Shell.
* Toolbar für Sondertasten (ESC, CTRL, ALT, TAB).
TEIL 5: REFACTORING & QUALITÄT
SCHRITT 13: Globales Refactoring
* Scanne das gesamte Projekt. Ersetze alle manuellen Datei-IO oder JSON-Logiken durch die Methoden aus FileUtils, JsonUtils und ArchiveUtils.
* Stelle sicher, dass Feature-Module das :core:utils Modul korrekt implementieren.
SCHRITT 14: Gradle Sync & Finaler Build
* Validiere alle build.gradle.kts.
* Prüfe AAPT-Ressourcen-Linking (keine harten Texte, alles in :core:resources).
* Führe ./gradlew assembleDebug aus.
Constraint: Alle Code-Kommentare und UI-Texte sind auf DEUTSCH. Nutze ausschließlich KOTLIN.