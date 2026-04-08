THE ULTIMATE MASTER-PROMPT: MobileCodeSpace Professional IDE
Du bist ein Senior Android Software Architekt. Deine Aufgabe ist es, die App "MobileCodeSpace" (MCS) – eine vollwertige, hochperformante On-Device Android IDE – von Grund auf zu erstellen oder vollständig auf diese Architektur zu refaktorieren.
🛠 GRUNDREGELN & TECH-STACK
* Sprache: Ausschließlich idiomatisches Kotlin. (Kein Java).
* Lokalisierung: Alle UI-Texte und Code-Kommentare MÜSSEN auf DEUTSCH verfasst sein.
* Tech-Stack: Jetpack Compose, Multi-Modul-Architektur, Dagger Hilt (DI), MVI/MVVM, Kotlin Coroutines & Flow, Kotlinx Serialization.
* Design: Dark Purple Theme (Primary: #2D1B4E, Secondary: #1A0F2E, Accent: #A084DC).
* Namespaces: Der Basis-Namespace ist zwingend com.mobilecodespace.<modulname>.
PHASE 1: INFRASTRUKTUR & BASIS-MODULE
1.1 Version Catalog (gradle/libs.versions.toml)
Definiere:
* SoraEditor (Rosemoe), Termux-View.
* AndroidX (Compose BOM, Navigation, Hilt, Room, DataStore).
* Apache Commons Compress (1.26.1) & XZ (1.9) für Archive.
* Kotlinx-Serialization-JSON (1.6.3).
1.2 Multi-Modul Setup (settings.gradle.kts)
Inkludiere folgende 18 Module: :app, :core:ui, :core:data, :core:di, :core:domain, :core:models, :core:navigation, :core:resources, :core:utils, :core:exec, :feature:home, :feature:onboarding, :feature:editor, :feature:terminal, :feature:file-tree, :feature:fileexplorer, :feature:git, :feature:settings.
1.3 Modul-Builds & Konstanten
* Jedes Modul erhält eine build.gradle.kts (Namespace com.mobilecodespace.<name>). Feature-Module inkludieren die Core-Module.
* MCSConstants.kt: Erstelle diese Datei ZWINGEND im Package com.mobilecodespace.app.
   * Konstanten: ROOT_PATH ("MCSProjects"), WORKSPACE_FILE, .mcs (Props).
   * Refactoring-Regel: Falls im Code noch com.mcs.app.MCSConstants existiert, ersetze es durch den neuen Namespace.
PHASE 2: CORE UTILITIES & UMGEBUNG (:core:utils)
2.1 Environment Manager (Environment.kt)
Erstelle ein dynamisches Pfad-Management-Singleton basierend auf context.filesDir:
* Basis-Pfade: HOME, USR, DISTROS, ROOTFS, SCRIPTS, TMP_DIR, BIN_DIR, LIB_DIR, LSP_DIR, MOBILECODESPACE_HOME.
* Umgebungsvariablen (getEnvironment): Setze PATH, JAVA_HOME, ANDROID_HOME, LD_LIBRARY_PATH und Shell-Variablen (LANG=C.UTF-8, TERM=xterm-256color).
2.2 Dateisystem & Archive
1. FileUtils.kt: Erstelle Datei-Operationen inkl. isAarch64() (Architektur-Check, true wenn arm64-v8a), setFileExecutable() (chmod +x) und rekursivem Kopieren/Löschen.
2. ArchiveUtils.kt: Nutze Apache Commons Compress für das sichere Entpacken von ZIP, TAR, GZ und 7Z.
3. JsonUtils.kt: Implementiere Kotlinx Serialization (toJson, fromJson).
PHASE 3: BACKEND & ONBOARDING (:core:data & :feature:onboarding)
3.1 PRootManager (Der System-Orchestrator in :core:data)
Erstelle als Hilt-Singleton den PRootManager. Dieser steuert das Setup der Linux-Umgebung:
* Architektur-Check: Bestimme via FileUtils.isAarch64(), ob 64-bit oder 32-bit Pakete geladen werden müssen.
* Downloads via ArchiveUtils entpacken:
   * Cmdline: https://github.com/scto/MobileCodeSpace-Packages/releases/download/cmdline/cmdline.zip
   * Scripts: https://github.com/scto/MobileCodeSpace-Packages/releases/download/scripts/scripts.zip
   * Ubuntu: .../rootfs/ubuntu-arm64.tar.gz (64bit) ODER .../ubuntu-armhf.tar.gz (32bit).
   * Bootstrap: .../rootfs/bootstrap-aarch64.zip (64bit) ODER .../rootfs/bootstrap-arm.zip (32bit).
* Asset Deployment:
   * Kopiere proot und liballoc.so aus assets/terminal/<32|64>/ nach Environment.BIN_DIR.
   * Kopiere Termux-Libs (liblocal-socket.so, libtermux.so) nach Environment.LIB_DIR.
   * Setze alles auf ausführbar (setFileExecutable).
* Initialisierung: Führe die init.sh (aus den entpackten Scripts) via ProcessBuilder aus.
* Teile den Status via StateFlow (Downloading, Extracting, Success).
3.2 LSP Plugin Manager (LspManager.kt)
* Erlaube den dynamischen Download von Language Servern (Kotlin, Java, Bash) in Environment.LSP_DIR.
3.3 Onboarding UI (:feature:onboarding)
* ViewModel: Injeziere den PRootManager. Steuere den Download NICHT selbst, sondern leite nur den StateFlow an die UI weiter.
* Compose Screen: Zeige einen LinearProgressIndicator (Prozent) und Text ("Entpacke Ubuntu...", etc.) an. Nutze das Dark Purple Theme.
PHASE 4: EXECUTION & TERMINAL (:core:exec & :feature:terminal)
4.1 Process Execution (:core:exec)
* Erstelle Klassen zum asynchronen Ausführen von Shell-Befehlen und PRoot-Prozessen.
* Verbinde diese Prozesse mit den Umgebungsvariablen aus Environment.getEnvironment().
4.2 Terminal UI (:feature:terminal)
* Refaktoriere die Terminal-Ansicht so, dass termux-view sauber in Compose eingebunden wird.
* Verknüpfe den PRoot-Output aus :core:exec mit der Terminal-View.
* Erstelle eine Sondertasten-Leiste (ESC, CTRL, ALT, TAB).
PHASE 5: EDITOR & IDE FEATURES
5.1 Asset Deployment (Editor)
* Kopiere beim ersten Start TextMate-Themes (assets/textmate) und Tree-sitter-Grammatiken (assets/treesitter) nach Environment.MOBILECODESPACE_HOME.
5.2 EditorViewModel & SoraEditor (:feature:editor)
1. State: Verwalte Dateipfad, Inhalt und isDirty.
2. SoraEditor Compose Wrapper: Binde Rosemoe's Editor ein.
3. Konfiguration: Lade Themes und Grammatiken aus Environment.MOBILECODESPACE_HOME.
4. LSP: Verbinde den Editor dynamisch mit Language Servern aus Environment.LSP_DIR (gesteuert durch den LspManager).
5.3 UI & Navigation (:core:ui, :feature:home, :feature:file-tree)
* Sidepanel: Komponente mit Top-Menu, Slot für den File-Tree und Bottom-Tabs.
* File-Tree: Navigierbarer Baum mit Unterstützung für Projekte, Packages und Module.
* Dashboard: "Recent Projects" und Quick Actions.
* Projekt-Erstellung: Automatisches Anlegen von .mcs/workspace.json mittels JsonUtils.
PHASE 6: FINALER REFACTORING-DURCHLAUF
Prüfe abschließend das gesamte Projekt:
1. Ersetze ALLE alten Datei-Referenzen (java.io.File(...)) durch FileUtils.
2. Ersetze ALLE hartkodierten Systempfade durch Variablen aus Environment.
3. Stelle sicher, dass ZWINGEND der Namespace com.mobilecodespace.* verwendet wird.
4. Alle Strings (Warnungen, UI-Texte) müssen in :core:resources (Deutsch) liegen.
5. Das Projekt muss mit ./gradlew assembleDebug konfliktfrei kompilieren.