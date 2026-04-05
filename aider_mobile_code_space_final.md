Aider Master-Prompt: MobileCodeSpace Professional IDE

Du bist ein Senior Android Software Architekt. Wir bauen "MobileCodeSpace" (MCS), eine hochperformante IDE für Android. Technologie: Kotlin, Jetpack Compose, Multi-Modul-Architektur, Hilt (DI), MVI/MVVM, Flow. Design: Dark Purple Theme (#2D1B4E) gemäß Referenzbildern. Sprache: Deutsche UI-Texte und deutsche Code-Kommentare.

SCHRITT 1: Version Catalog & Modul-Struktur

1. Erstelle/Aktualisiere gradle/libs.versions.toml:

   * SoraEditor (Rosemoe): editor, language-treesitter, language-textmate, language-java, editor-lsp.

   * Terminal: termux-view.

   * AndroidX: compose-bom, hilt-android, navigation-compose, room, datastore-preferences.

   * Plugins: android-application, android-library, kotlin-android, hilt-android, kotlin-serialization.

2. Initialisiere folgende 15 Module in settings.gradle.kts:

   * :app (Root-Activity, Manifest, Application)

   * :core:ui (Globales Theme & Shared Components)

   * :core:data, :core:di, :core:domain, :core:models, :core:navigation, :core:resources

   * :feature:home (Dashboard mit Quick Actions & Recent Projects)

   * :feature:onboarding (Setup: Permissions & PRoot-Installation)

   * :feature:editor (SoraEditor Integration & LSP)

   * :feature:terminal (Ubuntu PRoot Shell)

   * :feature:fileexplorer, :feature:git, :feature:settings

SCHRITT 2: Projekt-Infrastruktur & Build-Stabilität

1. Root build.gradle.kts: Definiere Plugins (alias(libs.plugins...)) ohne sie anzuwenden.

2. gradle.properties: Setze org.gradle.jvmargs=-Xmx4g, aktiviere parallel und configuration-cache.

3. Modul-Builds: Erstelle für jedes Modul eine build.gradle.kts. Achte auf korrekte namespace Definitionen (z.B. com.mcs.core.ui), um AAPT-Ressourcen-Fehler zu vermeiden.

4. Hilt Setup: Erstelle MCSApplication mit @HiltAndroidApp.

SCHRITT 3: Shared UI & Design System (:core:ui)

1. Theming: Implementiere MCSTheme (Primary: #2D1B4E, Secondary: #1A0F2E, Akzent: #A084DC).

2. Komponenten: - MCSCard: Abgerundete Karten (16dp) mit leichtem Border.

   * MCSActionButton: Die Buttons für den Home-Screen ("New Project", "Open" etc.) mit Icon und Subtext.

   * ProjectListItem: Komponente für die "Recent Projects" Liste.

SCHRITT 4: PRoot & Ubuntu Backend (:core:data)

1. PRootManager: - Logik zum Kopieren der proot Arm64-Binary aus Assets nach filesDir.

   * Download/Extraktion eines Ubuntu Rootfs und JDK/Build-Tools.

   * Prozess-Start: chmod +x setzen und via ProcessBuilder mit den Flags -0 -b /sdcard -r <rootfs_path> starten.

2. Services: Erstelle einen PRootService, der die Shell-Instanz im Hintergrund am Leben hält.

SCHRITT 5: SoraEditor Integration (:feature:editor)

1. EditorActivity & ViewModel: Verwalte geöffnete Dateien, LSP-Status und Undo/Redo-Stack.

2. SoraEditor View: - Erstelle einen Compose-Wrapper für io.github.Rosemoe.sora-editor:editor.

   * Implementiere TextMateColorScheme, GrammarProvider (Tree-sitter) und LSP-Support.

3. Funktionen: Implementiere Top-Bar Aktionen für Save, Save All, Undo, Redo, Cut, Copy, Paste, Format Code, Select All.

SCHRITT 6: Terminal Feature (:feature:terminal)

1. TerminalActivity & Screen: Hoste die termux-view.

2. Verknüpfung: Verbinde die Terminal-View mit dem Input/Output-Stream des PRoot-Ubuntu-Prozesses.

3. Toolbar: Erstelle eine Leiste für Sondertasten (ESC, CTRL, ALT, TAB, Pfeiltasten).

SCHRITT 7: Navigation & Flow-Logik (:app)

1. MCSActivity: Hostet den zentralen NavHost.

2. Einstiegs-Logik: - Prüfe beim Start: Sind Permissions (MANAGE_EXTERNAL_STORAGE) vorhanden? Ist Ubuntu installiert?

   * Falls NEIN: Navigiere zu :feature:onboarding.

   * Falls JA: Navigiere zu :feature:home.

3. Home-Screen Implementation: Setze das Design aus dem Screenshot um (Quick Actions Karten, Recent Projects Liste mit Pfaden wie /storage/emulated/0/...).

SCHRITT 8: Finalisierung & Build-Fixes

1. AAPT Validierung: Stelle sicher, dass alle XML-Ressourcen (Drawables, Layouts) wohlgeformt sind und keine korrupten Proto-XMLs entstehen.

2. Manifest: Registriere alle Activities, Provider (MCSFileProvider, MCSDocumentsProvider) und den PRootService. Fordere alle benötigten Berechtigungen an.

3. Dependency Check: Verifiziere, dass Feature-Module korrekt auf :core:ui, :core:domain, :core:models und :core:navigation zugreifen.

4. Settings-Modul: Erstelle SettingsActivity, SettingsScreen und SettingsViewModel zur Konfiguration von Editor-Schriftgröße, Themes und Pfaden.

Führe diese Schritte systematisch aus, um eine vollständig funktionale, multi-modulare Android IDE zu erstellen.
