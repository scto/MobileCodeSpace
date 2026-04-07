Aider Master-Prompt: MobileCodeSpace Professional IDE (Final v4.3)
Du bist ein Senior Android Software Architekt. Wir bauen "MobileCodeSpace" (MCS), eine hochperformante IDE für Android. Technologie: Kotlin, Jetpack Compose, Multi-Modul-Architektur, Hilt (DI), MVI/MVVM, Flow, Kotlin Serialization. Design: Dark Purple Theme (#2D1B4E), sekundäres Lila (#1A0F2E), Akzentfarbe (#A084DC). Sprache: Deutsche UI-Texte und deutsche Code-Kommentare.
TEIL 1: INFRASTRUKTUR & BUILD-STABILITÄT
SCHRITT 1: Version Catalog & Module
1. gradle/libs.versions.toml: Erstelle Einträge für SoraEditor, Termux-View, Hilt, Room, DataStore, Commons-Compress, Kotlinx-Serialization.
2. settings.gradle.kts: Inkludiere alle 17 Module (inkl. :core:utils, :core:resources, :feature:file-tree, etc.).
3. gradle.properties: Setze Xmx4g, parallel=true und configuration-cache=true.
4. Namespaces: Erstelle für jedes Modul eine build.gradle.kts. Jedes Modul MUSS einen eindeutigen namespace (com.mcs.<name>) haben.
SCHRITT 2: Globale Konstanten (:app)
Erstelle MCSConstants.kt im Modul :app:
* ROOT_PATH = "MCSProjects", WORKSPACE_FILE = "workspace.json".
* .mcs (Metadaten), .editor (geöffnete Dateien).
TEIL 2: CORE MANAGERS & UTILITIES (ZENTRAL)
SCHRITT 3: Erstellung des EnvironmentManagers (:core:utils)
Erstelle Environment.kt als Kotlin-Singleton (object), das Pfade dynamisch auflöst.
* Logik: Nutze context.filesDir als Basis. Definiere Pfade für HOME, USR, BIN_DIR, LIB_DIR, DISTROS, ROOTFS, SCRIPTS, TMP_DIR und LSP_DIR.
* Umgebungsvariablen: Implementiere getEnvironment(projectPath: String), um PATH, JAVA_HOME, ANDROID_HOME, LD_LIBRARY_PATH und Shell-spezifische Variablen (LANG, TERM) bereitzustellen.
* Initialisierung: Erstelle die Ordnerstruktur beim App-Start und setze archApp ("64" oder "32") basierend auf dem Architektur-Check.
SCHRITT 4: Erstellung des LSP Plugin Managers (:core:data)
Erstelle LspManager.kt (Hilt-Injectable Singleton).
* Funktion: Verwalte den Download, die Installation und Deinstallation von Language Servern.
* Installation: Download von ZIP-Archiven -> Entpacken in Environment.LSP_DIR -> Binary mittels FileUtils.setFileExecutable ausführbar machen.
* Modell: Nutze LspPlugin (ID, Name, Version, DownloadURL, BinaryPath).
* Zustand: Nutze StateFlow, um installierte Plugins an die UI (Settings/Plugins) zu melden.
SCHRITT 5: File & Archive Utilities (:core:utils)
1. FileUtils.kt: Implementiere Datei-Ops inkl. setFileExecutable, isAarch64() und rekursivem Kopieren/Löschen.
2. ArchiveUtils.kt: Support für ZIP, TAR, GZ, 7Z via Apache Commons Compress.
3. JsonUtils.kt: Kotlinx Serialization Wrapper für Datei-Persistenz.
TEIL 3: ASSET DEPLOYMENT & ONBOARDING
SCHRITT 6: Architektur-Check & Binary Deployment
Implementiere in :core:data:
* Kopiere proot und liballoc aus assets/terminal/<32|64>/ nach Environment.BIN_DIR basierend auf FileUtils.isAarch64().
* Kopiere Termux-Libs (liblocal-socket.so, libtermux.so) nach Environment.LIB_DIR.
* Kopiere TextMate-Themes und Tree-Sitter-Grammatiken nach Environment.MOBILECODESPACE_HOME.
* Scripts: Entpacke assets/scripts nach Environment.SCRIPTS und führe init.sh nach der Installation aus.
SCHRITT 7: Cloud-Onboarding-System (:feature:onboarding)
Implementiere architekturabhängige Downloads im OnboardingViewModel:
* URLs:
   * cmdline: https://github.com/scto/MobileCodeSpace-Packages/releases/download/cmdline/cmdline.zip
   * scripts: https://github.com/scto/MobileCodeSpace-Packages/releases/download/scripts/scripts.zip
   * Ubuntu: .../ubuntu-arm64.tar.gz (64) oder .../ubuntu-armhf.tar.gz (32).
   * Bootstrap: .../bootstrap-aarch64.zip (64) oder .../bootstrap-arm.zip (32).
* Ablauf: Download -> Progress UI -> ArchiveUtils extrahieren -> Bereinigung.
TEIL 4: EDITOR & IDE FEATURES
SCHRITT 8: Editor & LSP Integration (:feature:editor)
1. EditorViewModel: Verwalte EditorUiState (Inhalt, Pfad, Dirty-Flag). Nutze JsonUtils für Projekt-Metadaten.
2. SoraEditorView: Compose-Wrapper für Rosemoe's Editor.
   * Konfiguriere den Editor so, dass er Themes/Grammatiken aus Environment.MOBILECODESPACE_HOME lädt.
3. LSP-Connector: Verbinde den Editor dynamisch mit installierten Servern aus Environment.LSP_DIR (Kotlin/Java/XML/Bash).
SCHRITT 9: Dashboard & Sidepanel
* Dashboard: "Recent Projects" Liste und Quick Actions.
* Sidepanel: Implementiere die Komponente mit Top-Menu, Content-Slot (File-Tree) und Tab-Navigation.
* Settings/Plugins: UI zur manuellen Installation/Deinstallation von LSP-Plugins via LspManager.
TEIL 5: FINALE OPTIMIERUNG
SCHRITT 10: Refactoring & Sync
* Ersetze alle manuellen Pfade durch Environment Aufrufe.
* Ersetze alle Datei-IO durch FileUtils.
* Zentralisiere Strings in :core:resources.
* Validiere die Build-Stabilität und führe ./gradlew assembleDebug aus.
Constraint: Nutze ausschließlich Kotlin. Alle UI-Texte und Kommentare sind DEUTSCH.