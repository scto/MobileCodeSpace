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
SCHRITT 9: Build-Stabilität & Finalisierung (WICHTIG)

1. Dependency Check: Überprüfe alle build.gradle.kts Dateien. Stelle sicher, dass implementation(project(":core:ui")), :core:models, etc. korrekt in den Feature-Modulen vorhanden sind.

2. Resource Linking (AAPT Fix): - Stelle sicher, dass keine korrupten XML-Ressourcen vorhanden sind.

   * Benenne alle Icons konsistent (ic_launcher_background.xml etc.) und validiere die Vector-Drawables.

   * Prüfe auf doppelte Ressourcen-IDs über Modulgrenzen hinweg.

3. Hilt Setup: Verifiziere, dass jedes Modul mit @InstallIn korrekt konfiguriert ist und die MCSApplication die @HiltAndroidApp Annotation trägt.

4. ProGuard/R8: Erstelle eine proguard-rules.pro für den SoraEditor und PRoot-Binaries, um zu verhindern, dass kritischer nativer Code oder Reflection-Logik entfernt wird.

5. Finaler Build-Test: Führe einen clean und assembleDebug Task durch. Behebe alle Lint-Fehler und Namespace-Konflikte in den AndroidManifest.xml Dateien der Submodule.
SCHRITT 10: Gradle & Projekt-Konfiguration (Build-Vervollständigung)

10.1 Root build.gradle.kts

Erstelle die build.gradle.kts im Hauptverzeichnis:

plugins {

   alias(libs.plugins.android.application) apply false

   alias(libs.plugins.android.library) apply false

   alias(libs.plugins.kotlin.android) apply false

   alias(libs.plugins.hilt.android) apply false

   alias(libs.plugins.kotlin.serialization) apply false

}



10.2 settings.gradle.kts

Konfiguriere die Modul-Inklusion korrekt:

rootProject.name = "MobileCodeSpace"

include(":app")

include(":core:ui")

include(":core:data")

include(":core:di")

include(":core:domain")

include(":core:models")

include(":core:navigation")

include(":core:resources")

include(":feature:home")

include(":feature:onboarding")

include(":feature:editor")

include(":feature:terminal")

include(":feature:fileexplorer")

include(":feature:git")

include(":feature:settings")



dependencyResolutionManagement {

   repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

   repositories {

       google()

       mavenCentral()

       maven { url = uri("[https://jitpack.io](https://jitpack.io)") }

   }

}



10.3 gradle.properties

Optimale Einstellungen für große Multi-Modul-Projekte:

org.gradle.jvmargs=-Xmx4g -XX:MaxMetaspaceSize=1g -Dfile.encoding=UTF-8 -XX:+UseParallelGC

android.useAndroidX=true

android.nonTransitiveRClass=true

android.enableJetifier=false

kotlin.code.style=official

org.gradle.parallel=true

org.gradle.caching=true

org.gradle.configuration-cache=true



10.4 Beispiel: Build-Datei für ein Feature-Modul (:feature:editor)

plugins {

   alias(libs.plugins.android.library)

   alias(libs.plugins.kotlin.android)

   alias(libs.plugins.hilt.android)

   kotlin("kapt")

}



android {

   namespace = "com.mcs.feature.editor"

   compileSdk = 34



   defaultConfig {

       minSdk = 26

       testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

   }



   buildFeatures {

       compose = true

   }

   composeOptions {

       kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()

   }

}



dependencies {

   implementation(project(":core:ui"))

   implementation(project(":core:domain"))

   implementation(project(":core:models"))

   implementation(project(":core:navigation"))



   // SoraEditor & LSP

   implementation(libs.sora.editor)

   implementation(libs.sora.language.treesitter)

   implementation(libs.sora.language.textmate)

   implementation(libs.sora.editor.lsp)



   implementation(libs.androidx.compose.ui)

   implementation(libs.androidx.hilt.navigation.compose)

   implementation(libs.hilt.android)

   kapt(libs.hilt.compiler)

}



10.5 Beispiel: Build-Datei für das :app Modul

plugins {

   alias(libs.plugins.android.application)

   alias(libs.plugins.kotlin.android)

   alias(libs.plugins.hilt.android)

   kotlin("kapt")

}



android {

   namespace = "com.mcs.app"

   compileSdk = 34

   // ... Standard Application Config ...

}



dependencies {

   implementation(project(":core:ui"))

   implementation(project(":core:di"))

   implementation(project(":feature:home"))

   implementation(project(":feature:onboarding"))

   implementation(project(":feature:editor"))

   implementation(project(":feature:terminal"))

   // ... Weitere Feature-Module ...

   

   implementation(libs.hilt.android)

   kapt(libs.hilt.compiler)

}

SCHRITT 11: Globale Konstanten (:app)

Erstelle im Modul :app die Datei MCSConstants.kt:

object MCSConstants {

   const val ROOT_PATH = "MCSProjects" // Der Name des Basis-Ordners für Projekte

   const val WORKSPACE_FILE = "workspace.json"

   const val PROPS_PATH = ".mcs"

   const val EDITOR_PROPS_PATH = "$PROPS_PATH/.editor"

   const val EDITOR_PROPS_FILE = "open_files.json" // Relativ zu EDITOR_PROPS_PATH

   const val EDITOR_PROPS_FILE_BAK = "$EDITOR_PROPS_FILE.bak"

}



SCHRITT 12: Utility Modul (:core:utils)

Implementiere FileUtils.kt für Datei- und Ordner-Operationen:

* create, delete, rename, copy, move, load, save, chmod, getExtension.

* Unterstützung für rekursive Verzeichnis-Operationen.

SCHRITT 13: Ressourcen-Zentralisierung (:core:resources)

Zentralisiere alle res-Ordner. Ersetze hartkodierte Texte im gesamten Projekt durch deutsche String-Ressourcen in :core:resources.

SCHRITT 14: Sidepanel Komponente (:core:ui)

Komponente mit horizontalem Menü oben, dynamischem Hauptinhalt (Slot) und Tab-Leiste unten zur View-Umschaltung.

SCHRITT 15: File-Tree System (:feature:file-tree)

Implementiere FileTreeView mit FileNode, showHidden, sortOrder und listType (File, Package, Module). Erfordert einen Pfad-Parameter vom Editor-Modul.

SCHRITT 16: Editor UI Enhancements (:feature:editor)

* Sidepanel-Trigger (Button oben links).

* 3-Punkte-Menü (Open, Close, Save, Tools/Format/Search).

SCHRITT 17: Projekt-Initialisierung & Metadaten-Logik

Implementiere die Logik zur Erstellung eines neuen Projekts (in :core:data oder dem entsprechenden Repository):

1. Ordner-Struktur: Wenn ein neues Projekt erstellt wird, erstelle das Hauptverzeichnis des Projekts.

2. Metadaten-Verzeichnisse: - Erstelle innerhalb des Projektordners das Verzeichnis MCSConstants.ROOT_PATH (als Unterordner für interne Projektdaten).

   * Erstelle darin die Struktur für MCSConstants.EDITOR_PROPS_PATH (z.B. .mcs/.editor).

3. Initial-Dateien:

   * Erstelle eine leere workspace.json (WORKSPACE_FILE) im internen Metadaten-Ordner.

   * Erstelle die open_files.json (EDITOR_PROPS_FILE) und die zugehörige .bak Datei als initiale Platzhalter.

4. Validierung: Nutze die Funktionen aus :core:utils, um sicherzustellen, dass die Verzeichnisse beschreibbar sind und die chmod-Rechte für die spätere Nutzung im PRoot-Ubuntu-Container korrekt gesetzt sind.

SCHRITT 18: Build-Finalisierung

* Überprüfe alle build.gradle.kts auf Namespace-Konsistenz.

* Validiere die Ressourcen in :core:resources, um AAPT-Fehler ("failed to parse proto XML") zu vermeiden.

* Stelle sicher, dass die MCSActivity die Navigation zwischen Onboarding (Installation von OpenJDK/Build-Tools) und Home-Screen korrekt steuert.
