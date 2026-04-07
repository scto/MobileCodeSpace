Aider Master-Prompt: MobileCodeSpace Professional IDE (v3.0)
Du bist ein Senior Android Software Architekt. Wir bauen "MobileCodeSpace" (MCS), eine hochperformante IDE für Android. Technologie: Kotlin, Jetpack Compose, Multi-Modul-Architektur, Hilt, MVI. Sprache: Deutsche UI-Texte und deutsche Code-Kommentare.
SCHRITT 1: Infrastruktur & Version Catalog
1. gradle/libs.versions.toml:
   * SoraEditor, Terminal (termux-view), Hilt, Room, DataStore.
   * Archivierung: commons-compress (1.26.1).
   * JSON: kotlinx-serialization-json (1.6.3).
2. settings.gradle.kts: Alle 17 Module inkludieren.
3. gradle.properties: Xmx4g, parallel=true, configuration-cache=true.
SCHRITT 2: Build-Konfiguration & Namespaces
Erstelle für jedes Modul eine build.gradle.kts mit eindeutigen Namespaces (com.mcs.*). Feature-Module müssen :core:ui, :core:utils, :core:domain und :core:navigation implementieren.
SCHRITT 3: Core Utilities (:core:utils)
1. FileUtils.kt: Implementiere Datei-Operationen inkl. setFileExecutable (chmod +x) und isAarch64().
2. ArchiveUtils.kt: Nutze Apache Commons Compress für ZIP, TAR, GZ und 7Z.
3. JsonUtils.kt: Nutze Kotlinx Serialization.
4. Environment.kt: Implementiere das dynamische Pfad-Management relativ zum App-Speicher (siehe Referenz-Logik).
SCHRITT 4: Onboarding & Multi-Cloud-Download (:feature:onboarding)
Implementiere ein OnboardingViewModel mit architekturabhängigen Downloads:
1. Download-URLs:
   * cmdline.zip: https://github.com/scto/MobileCodeSpace-Packages/releases/download/cmdline/cmdline.zip
   * scripts.zip: https://github.com/scto/MobileCodeSpace-Packages/releases/download/scripts/scripts.zip
2. Architektur-abhängig (aarch64 vs. arm):
   * Ubuntu:
      * aarch64: .../rootfs/ubuntu-arm64.tar.gz
      * arm: .../rootfs/ubuntu-armhf.tar.gz
   * Bootstrap:
      * aarch64: .../rootfs/bootstrap-aarch64.zip
      * arm: .../rootfs/bootstrap-arm.zip
3. Logik:
   * Download -> Entpacken in Environment.ROOTFS / Environment.HOME.
   * Nach dem Entpacken von scripts.zip die Datei init.sh in Environment.SCRIPTS ausführen.
4. UI: OnboardingScreen mit Fortschrittsanzeige für jeden Teilschritt.
SCHRITT 5: UI & Features
1. MCSTheme: Dark Purple Design (#2D1B4E).
2. Sidepanel: Dynamisches Sidepanel-System in :core:ui.
3. Home Dashboard: Quick Actions und Recent Projects.
4. SoraEditor: Integration in :feature:editor mit LSP und Tree-sitter.
5. File-Tree: In :feature:file-tree mit Pfad-Parameter und Sortierung.
SCHRITT 6: PRoot Backend (:core:data)
1. PRootManager: Startet Ubuntu mit Flags -0 -b /sdcard -r <rootfs_path>.
2. ArchCheck: Kopiert die passende proot-Binary basierend auf FileUtils.isAarch64().
SCHRITT 7: Finalisierung & Refactoring
Überprüfe das gesamte Projekt und stelle sicher, dass alle Datei-Operationen über FileUtils und alle Pfade über Environment geladen werden. Validiere die Build-Stabilität und führe assembleDebug aus.