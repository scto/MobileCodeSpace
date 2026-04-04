Aider Prompt: MobileCodeSpace Professional IDE Setup

Du bist ein Senior Android Software Architekt. Wir bauen "MobileCodeSpace", eine hochperformante IDE für Android. Technologie: Kotlin, Jetpack Compose, Multi-Modul-Architektur, Hilt (DI), MVI/MVVM, Flow. Design: Dark Purple Theme (#2D1B4E) gemäß Referenzbild. Sprache: Deutsche UI-Texte und deutsche Code-Kommentare.

SCHRITT 1: Version Catalog & Modul-Struktur

1. Aktualisiere gradle/libs.versions.toml:

   * Gruppen für SoraEditor: sora-editor = "io.github.Rosemoe.sora-editor:editor:...", sora-language-textmate, sora-language-treesitter, sora-language-java, sora-editor-lsp.

   * Terminal: termux-view.

   * Standard: Compose BOM, Hilt, Navigation, Room, DataStore.

2. Initialisiere folgende Module in settings.gradle.kts:

   * :app

   * :core:data, :core:di, :core:domain, :core:models, :core:navigation, :core:resources

   * :feature:editor, :feature:terminal, :feature:explorer, :feature:settings, :feature:onboarding

SCHRITT 2: PRoot & Ubuntu Core (Backend)

1. Erstelle in :core:data den PRootManager:

   * Logik zum Kopieren der proot Arm64-Binary aus Assets nach filesDir.

   * Download/Extraktion eines Ubuntu Rootfs.

   * Ausführung: chmod +x setzen und Prozess mit -0 -b /sdcard -r <rootfs> starten.

2. Implementiere ein PRootService (Android Service), um die Ubuntu-Instanz im Hintergrund stabil zu halten.

SCHRITT 3: SoraEditor Integration (:feature:editor)

1. EditorActivity: Erstelle eine Activity, die den EditorScreen hostet.

2. EditorViewModel: Verwalte openFiles, undoStack, redoStack und den Speicherstatus.

3. SoraEditor View (Compose Wrapper):

   * Erstelle eine AndroidView, die den CodeEditor von Rosemoe instanziiert.

   * Providers: Implementiere TextMateColorScheme, GrammarProvider (für Java/Kotlin via Tree-sitter) und den AssetManager für Themes.

   * LSP: Integriere sora-editor-lsp, um Autovervollständigung und Fehlerdiagnose zu ermöglichen.

4. Funktionen: Implementiere Methoden für selectAll(), cut(), paste(), undo(), redo(), formatCode(), saveFile() und saveAll().

SCHRITT 4: Terminal & PRoot Shell (:feature:terminal)

1. TerminalActivity: Hostet den TerminalScreen.

2. TerminalViewModel: Verbindet termux-view mit dem InputStream/OutputStream des PRoot-Ubuntu-Prozesses.

3. TerminalScreen: Implementiere eine interaktive Konsole mit einer Custom-Keyboard-Leiste für CTRL, ESC, TAB und Pfeiltasten.

SCHRITT 5: Einstellungen (:feature:settings)

1. SettingsActivity: Hostet den SettingsScreen.

2. SettingsViewModel: Verwalte Präferenzen wie Theme-Farben, Schriftgröße des Editors, PRoot-Mount-Pfade und Plugin-Verwaltung.

3. SettingsScreen: Erstelle eine saubere Material 3 Liste für alle Konfigurationen.

SCHRITT 6: Navigation & UI-Integration (:app)

1. Implementiere die MainActivity als Einstiegspunkt.

2. Erstelle ein zentrales NavHost in :core:navigation, das zwischen Onboarding, Editor (Hauptscreen), Terminal und Settings navigiert.

3. Design-Finish:

   * Implementiere das "Dark Purple" Design global über :core:resources.

   * Nutze Glasmorphismus für den File-Explorer (Side-Drawer).

   * Stelle sicher, dass die App beim Start prüft, ob das Ubuntu-Rootfs installiert ist (Onboarding-Flow).

SCHRITT 7: Datei-Management & LSP-Sync

1. Verknüpfe den :feature:explorer so, dass Klicks auf Dateien die EditorActivity mit dem entsprechenden Pfad triggern.

2. Implementiere den FileProvider in :core:data, um Dateien sowohl im Android-Bereich als auch innerhalb des PRoot-Containers zu bearbeiten.
