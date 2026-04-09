MobileCodeSpace Refactoring Guide (Optimiert)
Folge diesen Schritten nacheinander. Warte nach jedem Prompt, bis Aider die Änderungen committet hat.
Schritt 1: Konstanten-Migration
Vorbereitung:
/add core/data/src/main/java/com/mobilecodespace/core/data/proot/PRootManager.kt
/add feature/onboarding/src/main/java/com/mobilecodespace/feature/onboarding/OnboardingViewModel.kt
/add app/src/main/java/com/mobilecodespace/app/MCSConstants.kt

Prompt 1:
Du bist ein Senior Android Architekt. Wir führen eine Konstanten-Migration durch.
1. Ersetze jeden Import von com.mcs.app.MCSConstants durch com.mobilecodespace.app.MCSConstants.
2. Finde im PRootManager und OnboardingViewModel hartkodierte Download-URLs (Ubuntu, PRoot, Rootfs, Libs).
3. Ersetze diese Strings durch die passenden Konstanten aus MCSConstants.
4. Achte auf korrekte Syntax, um Build-Fehler zu vermeiden.
Schritt 2: Infrastruktur für :core:exec
Vorbereitung:
/add settings.gradle.kts

Prompt 2:
1. Erstelle ein neues Submodul :core:exec.
2. Erstelle die Ordnerstruktur core/exec/src/main/kotlin/com/mobilecodespace/core/exec/.
3. Erstelle eine build.gradle.kts für das Modul mit dem Namespace com.mobilecodespace.core.exec.
4. Registriere das Modul in der settings.gradle.kts.
Schritt 3: Migration & Package-Anpassung (KEINE Konvertierung)
Vorbereitung: Alle Dateien aus dem temp-Ordner hinzufügen.
/add temp/exec/*
/add temp/terminal/*

Prompt 3:
Wir verschieben nun die Legacy-Dateien in ihre Ziel-Module.
1. Verschiebung 1: Verschiebe alle Dateien aus temp/exec/ in das neue Modul :core:exec (Zielpfad: core/exec/src/main/[java oder kotlin]/com/mobilecodespace/core/exec/).
2. Verschiebung 2: Verschiebe alle Dateien aus temp/terminal/ in das Modul :feature:terminal (Zielpfad: feature/terminal/src/main/[java oder kotlin]/com/mobilecodespace/feature/terminal/).
3. KEINE KONVERTIERUNG: Behalte das ursprüngliche Dateiformat bei (.java bleibt .java, .kt bleibt .kt).
4. Package-Refactoring: Aktualisiere in ALLEN verschobenen Dateien die package-Deklaration entsprechend dem neuen Modul-Pfad (com.mobilecodespace.core.exec bzw. com.mobilecodespace.feature.terminal).
5. Import-Refactoring: Korrigiere alle Imports im gesamten Projekt, die auf diese verschobenen Klassen verweisen, damit sie die neuen Package-Namen nutzen.
6. Übersetze alle Kommentare in diesen Dateien auf DEUTSCH.
Schritt 4: Architektur-Modernisierung (:core:exec)
Vorbereitung:
/add core/utils/src/main/kotlin/com/mobilecodespace/core/utils/Environment.kt
/add core/utils/src/main/kotlin/com/mobilecodespace/core/utils/FileUtils.kt

Prompt 4:
Modernisiere nun den Code im Modul :core:exec.
1. Refaktoriere den Code auf moderne Kotlin-Standards. Konvertiere verbliebene Java-Dateien in idiomatisches Kotlin.
2. Nutze Coroutines und Flow anstelle von alten Threading- oder Callback-Modellen.
3. Zwingende Regel: Nutze ausschließlich Environment.kt aus :core:utils für alle Verzeichnis-Zugriffe.
4. Zwingende Regel: Nutze ausschließlich FileUtils.kt aus :core:utils für Datei-Operationen.
5. Stelle sicher, dass :core:exec als Abhängigkeit in der build.gradle.kts von :core:data eingetragen ist.
Schritt 5: Terminal-Logik & Hilt (:feature:terminal)
Vorbereitung:
/drop
/add feature/terminal/src/main/kotlin/com/mobilecodespace/feature/terminal/**/*

Prompt 5:
Finales Refactoring der Terminal-Logik.
1. Konvertiere alle verbliebenen Dateien in :feature:terminal nach Kotlin.
2. Binde die Logik an die Jetpack Compose UI bzw. die termux-view an.
3. Verknüpfe die Session-Logik mit dem neuen :core:exec Modul zum Prozess-Start.
4. Richte Hilt für die Dependency Injection ein (@Inject, @Module).
5. Schreibe alle UI-Texte und Kommentare auf DEUTSCH.
6. Stelle die Kompilierbarkeit des gesamten Projekts sicher.