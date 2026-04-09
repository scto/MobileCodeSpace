MobileCodeSpace Refactoring Guide für Aider
Folge diesen Schritten nacheinander. Warte nach jedem Prompt, bis Aider die Änderungen abgeschlossen und einen Commit erstellt hat.
Schritt 1: Konstanten-Migration
Vorbereitung: Füge die Dateien hinzu, die die Konstanten verwenden. Ersetze die Platzhalter ... durch die tatsächlichen Pfade in deinem Projekt.
/add app/src/main/java/com/mobilecodespace/app/PRootManager.kt
/add app/src/main/java/com/mobilecodespace/app/ui/onboarding/OnboardingViewModel.kt
/add app/src/main/java/com/mobilecodespace/app/MCSConstants.kt

Prompt 1:
Du bist ein Senior Android Architekt. Wir führen eine Konstanten-Migration im Projekt "MobileCodeSpace" durch.
1. Durchsuche den aktuellen Kontext und ersetze jeden Import von com.mcs.app.MCSConstants durch com.mobilecodespace.app.MCSConstants.
2. Finde alle Stellen (wie z.B. im PRootManager oder OnboardingViewModel), an denen Download-URLs für Ubuntu, PRoot, Rootfs oder Libs hartkodiert sind. Ersetze diese hartkodierten Strings durch die passenden Konstanten aus der neuen MCSConstants Datei.
3. Achte penibel darauf, dass die Syntax korrekt bleibt, um Build-Fehler zu vermeiden. Nutze für die Pfade in Kotlin die entsprechenden Property-Referenzen.
Schritt 2: Infrastruktur für das neue Modul :core:exec
Vorbereitung: Lade die zentrale Gradle-Konfiguration.
/add settings.gradle.kts

Prompt 2:
Wir bauen die Multi-Modul-Architektur aus.
1. Erstelle ein neues Submodul namens :core:exec.
2. Lege dafür die nötige Ordnerstruktur an (core/exec/src/main/kotlin/...).
3. Erstelle eine build.gradle.kts Datei für dieses Modul. Der Namespace muss com.mobilecodespace.core.exec lauten. Nutze moderne Kotlin-DSL.
4. Trage das neue Modul :core:exec in die settings.gradle.kts ein.
Schritt 3: Migration & Kotlin-Konvertierung
Vorbereitung: Lade die Dateien aus dem temp-Verzeichnis (außer Terminal-Dateien).
/add temp/*.java
/add temp/*.kt

Prompt 3:
Wir migrieren nun Legacy-Code in das neue Modul.
1. Analysiere alle Dateien aus dem Ordner temp (ignoriere Unterordner wie temp/terminal).
2. Verschiebe diese Dateien in das neu erstellte Modul :core:exec.
3. Wichtig: Konvertiere jeglichen Java-Code während des Verschiebens in idiomatisches Kotlin.
4. Übersetze alle Code-Kommentare in diesen Dateien auf DEUTSCH.
5. Behalte die Logik zur Ausführung von Shell-Befehlen und Prozess-Verwaltung exakt bei (kein Funktionsverlust).
Schritt 4: Modernisierung (Coroutines & Utils)
Vorbereitung: Füge die Utility-Klassen und die Gradle-Dateien betroffener Module hinzu.
/add core/utils/src/main/kotlin/com/mobilecodespace/core/utils/Environment.kt
/add core/utils/src/main/kotlin/com/mobilecodespace/core/utils/FileUtils.kt
/add core/data/build.gradle.kts

Prompt 4:
Der Code in :core:exec muss nun an unsere moderne Architektur angepasst werden.
1. Refaktoriere den Code, sodass er moderne Kotlin-Standards nutzt: Ersetze alte Threading-Modelle oder Callbacks durch Coroutines (suspend functions) und Flow.
2. Zwingende Regel: Nutze für alle Pfad-Auflösungen (Data-Dir, Files-Dir etc.) ab sofort ausschließlich die Environment.kt aus dem Modul :core:utils. Entferne alte, hartkodierte Pfad-Logik.
3. Zwingende Regel: Nutze für alle Dateioperationen ab sofort ausschließlich die FileUtils.kt aus :core:utils.
4. Prüfe, ob :core:exec als Abhängigkeit in core:data benötigt wird und füge implementation(project(":core:exec")) falls nötig hinzu.
Schritt 5: Terminal-Logik Finalisierung
Vorbereitung: Alten Kontext bereinigen und Terminal-Dateien laden.
/drop
/add temp/terminal/*
/add feature/terminal/src/main/kotlin/com/mobilecodespace/feature/terminal/**/*

Prompt 5:
Letzter Schritt: Refactoring der Terminal-Session-Logik.
1. Migriere die Dateien aus dem Ordner temp/terminal/ in das bestehende Modul :feature:terminal.
2. Konvertiere Java-Code in Kotlin und schreibe alle Kommentare sowie UI-Strings auf DEUTSCH.
3. Verbinde die Logik sauber mit der Jetpack Compose UI (falls vorhanden) bzw. der termux-view.
4. Verknüpfe die Terminal-Session-Logik mit dem neuen :core:exec Modul, um Prozesse zu starten.
5. Entferne veraltete Importe und richte Hilt (@Inject, @Module) für die Dependency Injection in diesen Klassen ein.
6. Stelle sicher, dass der Code kompilierbar ist und keine ungelösten Referenzen bestehen.