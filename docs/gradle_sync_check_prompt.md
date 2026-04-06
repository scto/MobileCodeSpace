Aider Prompt: Gradle Multi-Modul & Dependency Sync
Du bist ein Experte für Gradle Build-Systeme und Android-Architektur. Deine Aufgabe ist es, die Integrität aller 17 Module des Projekts "MobileCodeSpace" sicherzustellen.
Analyse-Auftrag:
1. Überprüfe alle 17 Module auf das Vorhandensein einer build.gradle.kts:
   * :app, :core:ui, :core:data, :core:utils, :core:resources, :core:di, :core:domain, :core:models, :core:navigation
   * :feature:home, :feature:onboarding, :feature:editor, :feature:terminal, :feature:file-tree, :feature:fileexplorer, :feature:git, :feature:settings
2. Falls eine Datei fehlt, erstelle sie basierend auf dem Modul-Typ (Application vs. Library).
Strikte Regeln für die Implementierung:
1. Version Catalog Pflicht
* Nutze ausschließlich libs.versions.toml für alle Plugins und Libraries.
* Beispiel: alias(libs.plugins.android.library) statt fester Versionen.
* Beispiel: implementation(libs.androidx.compose.ui) statt String-Literalen.
2. Namespace Konsistenz (AAPT-Schutz)
* Jedes Modul MUSS einen eindeutigen namespace im android {} Block haben.
* Schema: com.mcs. + Modulname (z.B. com.mcs.feature.editor oder com.mcs.core.utils).
* Dies ist zwingend erforderlich, um "failed to parse proto XML" Fehler zu verhindern.
3. Modul-Abhängigkeiten (Dependency Graph)
* Feature-Module: Müssen :core:ui, :core:navigation, :core:domain und :core:models implementieren.
* Modul :feature:editor: Muss zusätzlich libs.sora.editor und die LSP/Tree-sitter Erweiterungen enthalten.
* Modul :core:utils: Muss libs.archive.commons.compress, libs.archive.xz und libs.serialization.json enthalten.
* Hilt: Jedes Modul benötigt die Hilt-Plugins und die hilt-android Library sowie den Kapt-Compiler.
4. Kotlin Serialization
* Überprüfe, ob das kotlin-serialization Plugin in den Modulen aktiviert ist, die JsonUtils oder @Serializable Models nutzen.
5. Compose Konfiguration
* Stelle sicher, dass in allen UI-Modulen (:core:ui und alle :feature:*) der buildFeatures { compose = true } Block korrekt gesetzt ist und die kotlinCompilerExtensionVersion aus dem Catalog geladen wird.
Ausführung:
* Korrigiere fehlende implementation(project(":...")) Aufrufe, wenn Code aus einem anderen Modul referenziert wird.
* Entferne doppelte oder widersprüchliche Deklarationen.
* Stelle sicher, dass die compileSdk und minSdk projektweit einheitlich sind (idealerweise über Variablen oder den Catalog).
Ziel: Nach Ausführung dieses Prompts muss das Projekt mit ./gradlew assembleDebug ohne "Missing Dependency" oder "Namespace Conflict" Fehler durchlaufen.