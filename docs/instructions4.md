Aider Master-Prompt: MobileCodeSpace Professional IDE (Final v4.0)
Du bist ein Senior Android Software Architekt. Wir bauen "MobileCodeSpace" (MCS), eine hochperformante IDE für Android. Technologie: Kotlin, Jetpack Compose, Multi-Modul-Architektur, Hilt (DI), MVI/MVVM, Flow, Kotlin Serialization. Design: Dark Purple Theme (#2D1B4E), sekundäres Lila (#1A0F2E), Akzentfarbe (#A084DC). Sprache: Deutsche UI-Texte und deutsche Code-Kommentare.
TEIL 1: INFRASTRUKTUR & BUILD-SYSTEM
SCHRITT 1: Version Catalog & Projekt-Setup
1. gradle/libs.versions.toml: Definiere Versionen für SoraEditor, Termux-View, Hilt, Room, DataStore.
   * Archivierung: commons-compress (1.26.1), xz (1.9).
   * JSON: kotlinx-serialization-json (1.6.3).
2. settings.gradle.kts: Inkludiere alle 17 Module (inkl. :core:utils, :core:resources, :feature:file-tree, etc.).
3. gradle.properties: Setze Xmx4g, parallel=true und configuration-cache=true.
SCHRITT 2: Modul-Konfiguration & Namespaces
Erstelle für jedes Modul eine build.gradle.kts.
* Zwingend: Jedes Modul benötigt einen eindeutigen namespace (com.mcs.<name>).
* Nutze ausschließlich den Version Catalog (libs.plugins...).
* Aktiviere kotlin-serialization in Modulen, die @Serializable nutzen.
SCHRITT 3: Globale Konstanten & Environment (:app & :core:utils)
1. MCSConstants.kt: Pfade für ROOT_PATH ("MCSProjects"), WORKSPACE_FILE, .mcs (Props) und .editor.
2. Environment.kt: Implementiere dynamische Pfadverwaltung relativ zu context.filesDir. Bereite Umgebungsvariablen für Shell-Prozesse vor (HOME, PREFIX, JAVA_HOME, etc.).
TEIL 2: CORE UTILITIES & ASSET-MANAGEMENT
SCHRITT 4: Core Utilities (:core:utils)
1. FileUtils.kt: Implementiere Funktionen für Datei/Ordner-Ops (create, delete, rename, copy, move, load, save, chmod).
   * Inkludiere isAarch64() (Architektur-Check) und setFileExecutable().
2. ArchiveUtils.kt: Support für ZIP, TAR, GZ, 7Z mittels Apache Commons Compress.
3. JsonUtils.kt: Kotlinx Serialization Wrapper für Datei-Persistenz.
SCHRITT 5: Architektur-spezifisches Deployment
Implementiere in :core:data die Logik zur Vorbereitung der Umgebung:
* Erkenne Architektur (arm vs aarch64) via FileUtils.
* Kopiere proot und liballoc aus assets/terminal/<arch>/ (32 oder 64) nach Environment.BIN_DIR.
* Kopiere Termux-Libs (liblocal-socket.so, libtermux.so) aus assets/terminal/termux/lib/arm64-v8a (falls 64-bit) nach Environment.LIB_DIR.
* Kopiere TextMate-Files (assets/textmate) und Tree-sitter Grammatiken (assets/treesitter) nach Environment.MOBILECODESPACE_HOME.
* Setze alle Binaries auf setFileExecutable(true).
TEIL 3: ONBOARDING & CLOUD-SETUP
SCHRITT 6: Cloud-Download-System (:feature:onboarding)
Implementiere das OnboardingViewModel mit architekturabhängigen Downloads:
* Statische URLs:
   * cmdline: https://github.com/scto/MobileCodeSpace-Packages/releases/download/cmdline/cmdline.zip
   * scripts: https://github.com/scto/MobileCodeSpace-Packages/releases/download/scripts/scripts.zip
* Architektur-abhängig (aarch64 vs arm):
   * Ubuntu: ubuntu-arm64.tar.gz (64) oder ubuntu-armhf.tar.gz (32).
   * Bootstrap: bootstrap-aarch64.zip (64) oder bootstrap-arm.zip (32).
* Ablauf: Download -> Entpacken -> scripts.zip nach Environment.SCRIPTS entpacken -> Aufruf von init.sh via ProcessBuilder.
* UI: OnboardingScreen mit Fortschrittsbalken und Status-Updates für jeden Teilschritt.
TEIL 4: EDITOR & IDE FEATURES
SCHRITT 7: Editor-Logik & State (:feature:editor)
1. EditorViewModel:
   * Verwalte EditorUiState (currentFilePath, content, isDirty, isLoading).
   * Lade/Speichere Dateien asynchron via FileUtils auf Dispatchers.IO.
   * Stelle TextMate-Konfigurationen (Themes/Languages) bereit, die auf Environment.MOBILECODESPACE_HOME verweisen.
   * Implementiere toggleSidepanel() für den Sidepanel-Zustand.
SCHRITT 8: SoraEditor Compose-Wrapper
Erstelle eine SoraEditorView (Compose-Wrapper):
* Nutze AndroidView, um Rosemoe's CodeEditor einzubinden.
* Binde die Daten aus dem EditorViewModel (Content, Themes) an den Editor.
* Implementiere Listener für Content-Änderungen, um den isDirty-State im ViewModel zu aktualisieren.
SCHRITT 9: LSP-Connector & Tree-sitter
Implementiere die LSP-Schnittstelle in :feature:editor:
* Starte LSP-Server (z.B. Java/Kotlin) aus Environment.BIN_DIR.
* Verbinde den Editor-LSP-Client mit dem lokalen Server-Prozess.
* Lade Tree-sitter Grammatiken aus dem internen Speicher für performantes Syntax-Highlighting.
SCHRITT 10: Sidepanel & Navigation
1. Sidepanel: Komponente in :core:ui mit Top-Menü, dynamischem Inhaltsbereich (z.B. File-Tree) und Tab-Navigation unten.
2. FileTreeView: Implementiere in :feature:file-tree mit Pfad-Parameter. Unterstütze File/Package/Module-Ansichten.
3. Projekt-Init: Beim Erstellen eines neuen Projekts müssen Metadaten-Ordner und workspace.json via FileUtils angelegt werden.
TEIL 5: REFACTORING & FINALE VALIDIERUNG
SCHRITT 11: Globales Refactoring & Sync
* Scanne das gesamte Projekt.
* Ersetze alle manuellen Pfade durch Aufrufe von Environment.
* Ersetze manuelle Datei-IO durch FileUtils und JSON-Parsing durch JsonUtils.
* Zentralisiere alle harten Texte in :core:resources (strings.xml).
SCHRITT 12: Build-Stabilität
* Validiere Namespace-Konsistenz in allen Manifesten.
* Prüfe, ob alle Module den Version Catalog korrekt nutzen.
* Führe ./gradlew assembleDebug aus und behebe alle Fehler.
Constraint: Nutze ausschließlich Kotlin. Alle UI-Texte und Kommentare im Code sind DEUTSCH.