Aider Master-Prompt: MobileCodeSpace Professional IDE (Final v3.6)
Du bist ein Senior Android Software Architekt. Wir bauen "MobileCodeSpace" (MCS), eine hochperformante IDE für Android. Technologie: Kotlin, Jetpack Compose, Multi-Modul-Architektur, Hilt (DI), MVI/MVVM, Flow, Kotlin Serialization. Design: Dark Purple Theme (#2D1B4E). Sprache: Deutsche UI und Code-Kommentare.
TEIL 1: INFRASTRUKTUR & BUILD
SCHRITT 1: Version Catalog & Module
1. gradle/libs.versions.toml: SoraEditor, Termux-View, Hilt, Room, DataStore, Commons-Compress, Kotlinx-Serialization.
2. settings.gradle.kts: Alle 17 Module inkludieren.
3. gradle.properties: JVM-Args -Xmx4g, Parallel-Build, Config-Cache.
SCHRITT 2: Build-Konfiguration & Namespaces
* Jedes Modul erhält eine build.gradle.kts mit Namespace com.mcs.<name>.
* Feature-Module implementieren :core:ui, :core:utils, :core:resources, :core:navigation.
SCHRITT 3: Globale Konstanten & Environment
1. MCSConstants.kt: Pfade für Projekte und Metadaten (.mcs).
2. Environment.kt: Dynamische Pfadverwaltung (relativ zu context.filesDir). Implementiere die Logik zum Laden der Umgebungsvariablen.
TEIL 2: UTILITIES & ASSET HANDLING
SCHRITT 4: Core Utilities (:core:utils)
1. FileUtils.kt: Implementiere Datei-Ops inkl. setFileExecutable, isAarch64() und rekursivem Handling.
2. ArchiveUtils.kt: Support für ZIP, TAR, GZ, 7Z.
3. JsonUtils.kt: Kotlinx Serialization Support.
SCHRITT 5: Architektur-Spezifisches Asset-Deployment
Implementiere die Architektur-Prüfung in :core:data:
* Prüfe via FileUtils.isAarch64().
* Kopiere proot und liballoc aus assets/terminal/<arch>/ (wobei arch = 32 oder 64) nach Environment.BIN_DIR.
* Kopiere Termux-Libs (liblocal-socket.so, libtermux.so) aus assets/terminal/termux/lib/arm64-v8a (falls aarch64) nach Environment.LIB_DIR.
* Setze alle Binaries auf setFileExecutable(true).
SCHRITT 6: Editor Assets & Tree-Sitter Deployment
* Kopiere beim ersten Start alle TextMate-Files aus assets/textmate und Tree-Sitter-Files aus assets/treesitter in den internen Speicher (Environment.MOBILECODESPACE_HOME).
* Diese Dateien dienen als Basis für das Syntax-Highlighting und die LSP-Funktionalität.
TEIL 3: ONBOARDING & SETUP
SCHRITT 7: Cloud-Download-System (:feature:onboarding)
Implementiere architekturabhängige Downloads im OnboardingViewModel:
* URLs:
   * cmdline: https://github.com/scto/MobileCodeSpace-Packages/releases/download/cmdline/cmdline.zip
   * scripts: https://github.com/scto/MobileCodeSpace-Packages/releases/download/scripts/scripts.zip
   * Ubuntu: .../ubuntu-arm64.tar.gz (64) oder .../ubuntu-armhf.tar.gz (32).
   * Bootstrap: .../bootstrap-aarch64.zip (64) oder .../bootstrap-arm.zip (32).
* Aktion: Download -> Entpacken -> scripts.zip nach Environment.SCRIPTS entpacken -> Aufruf von init.sh via ProcessBuilder.
TEIL 4: IDE FEATURES
SCHRITT 8: Sidepanel & UI (:core:ui)
* Komponente mit Top-Menu, Haupt-Content-Slot und Bottom-Tabs.
* Dark Purple Theme Implementation.
SCHRITT 9: Projekt-Management & File-Tree
* FileTreeView mit Unterstützung für File/Package/Module-View.
* Automatisches Anlegen von workspace.json und .mcs Metadaten bei Projekterstellung unter Verwendung von FileUtils.
SCHRITT 10: SoraEditor & LSP Integration (:feature:editor)
* Editor-Setup: Binde Rosemoe's SoraEditor ein.
* TextMate Integration: Konfiguriere den Editor so, dass er .tmTheme und .tmLanguage Dateien direkt aus Environment.MOBILECODESPACE_HOME lädt.
* LSP & Tree-Sitter: Implementiere die LSP-Logik so, dass sie die Tree-sitter Binaries und Grammatiken im entsprechenden Unterverzeichnis von Environment.MOBILECODESPACE_HOME findet und initialisiert.
* UI: Sidepanel-Trigger oben links, Action-Menu oben rechts (Save, Search, Format).
SCHRITT 11: Terminal & PRoot Shell (:feature:terminal)
* Anbindung der termux-view an die PRoot-Ubuntu-Shell unter Verwendung der Pfade aus Environment.
* Toolbar für Sondertasten (ESC, CTRL, ALT, TAB).
TEIL 5: QUALITÄTSSICHERUNG
SCHRITT 12: Refactoring & Sync
* Ersetze alle manuellen Pfade durch Environment Aufrufe.
* Ersetze alle Datei-IO durch FileUtils.
* Validiere Namespace-Konsistenz und führe ./gradlew assembleDebug aus.