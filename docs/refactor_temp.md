Aider Refactoring-Schritte für MobileCodeSpace
Führe diese Prompts nacheinander aus. Gib aider Zeit, jeden Schritt abzuschließen und zu committen, bevor du den nächsten einfügst.

Schritt 1: Die Konstanten-Migration (Suchen & Ersetzen)
Füge vorher idealerweise die Dateien zum Chat hinzu, von denen du weißt, dass sie Konstanten nutzen 
(z.B. /add core/data/src/main/java/com/mobilecodespace/core/data/proot/PRootManager.kt 
/add feature/onboarding/src/main/java/com/mobilecodespace/feature/ onboarding/OnboardingViewModel.kt).
Prompt 1:
Du bist ein Senior Android Architekt. Wir führen eine Konstanten-Migration im Projekt "MobileCodeSpace" durch.
1. Durchsuche den aktuellen Kontext und ersetze jeden Import von com.mcs.app.MCSConstants durch com.mobilecodespace.app.MCSConstants.
2. Finde alle Stellen (wie z.B. im PRootManager oder OnboardingViewModel), an denen Download-URLs für Ubuntu, PRoot, Rootfs oder Libs hartkodiert sind. Ersetze diese hartkodierten Strings durch die passenden Konstanten aus der neuen MCSConstants Datei.
3. Achte penibel darauf, dass die Syntax korrekt bleibt, um Build-Fehler zu vermeiden.
Schritt 2: Das neue Modul anlegen (Infrastruktur)
Hier geht es nur darum, Gradle und die Ordnerstruktur aufzusetzen, noch keinen Code verschieben! Lade dazu die settings.gradle.kts in den Chat.
Prompt 2:
Wir bauen die Multi-Modul-Architektur aus.
1. Erstelle ein neues Submodul namens :core:exec.
2. Lege dafür die nötige Ordnerstruktur an und erstelle eine build.gradle.kts Datei. Der Namespace in der Gradle-Datei muss com.mobilecodespace.core.exec lauten.
3. Trage das neue Modul :core:exec in die settings.gradle.kts ein.
Schritt 3: Dateien verschieben & zu Kotlin konvertieren
Lade nun die Dateien aus dem temp Verzeichnis (außer temp/terminal) in den Chat.
Prompt 3:
Wir migrieren nun Legacy-Code.
1. Analysiere alle Dateien, die gerade aus dem Basis-Ordner temp im Chat sind (ignoriere alles unterhalb von temp/terminal).
2. Verschiebe diese Dateien in das neu erstellte Modul :core:exec.
3. Wichtig: Konvertiere jeglichen Java-Code während des Verschiebens in idiomatisches Kotlin.
4. Übersetze alle Code-Kommentare in diesen Dateien auf DEUTSCH.
5. Behalte die Logik zur Ausführung von Shell-Befehlen und Prozess-Verwaltung exakt bei (kein Funktionsverlust).
Schritt 4: Architektur im neuen Modul anpassen (Refactoring)
Behalte die Dateien aus Schritt 3 im Chat. Füge Environment.kt und FileUtils.kt aus :core:utils zum Chat hinzu.
Prompt 4:
Der Code in :core:exec muss nun an unsere moderne Architektur angepasst werden.
1. Refaktoriere den Code, sodass er moderne Kotlin-Standards nutzt (Coroutines und Flow anstelle von alten Threading- oder Callback-Modellen).
2. Zwingende Regel: Nutze für alle Pfad-Auflösungen ab sofort ausschließlich die Environment.kt aus dem Modul :core:utils. Entferne alte Pfad-Logik.
3. Zwingende Regel: Nutze für alle Dateioperationen ab sofort ausschließlich die FileUtils.kt aus :core:utils.
4. Prüfe, ob :core:exec in Modulen wie :core:data als Abhängigkeit in deren build.gradle.kts eingetragen werden muss, und hole das ggf. nach.
Schritt 5: Terminal-Logik migrieren
Entferne die alten Dateien aus dem Chat (/drop). Füge nun die Dateien aus temp/terminal/ sowie die Ziel-Dateien aus :feature:terminal hinzu.
Prompt 5:
Letzter Schritt: Refactoring der Terminal-Session-Logik.
1. Migriere die Dateien aus dem Ordner temp/terminal/ in das bestehende Modul :feature:terminal.
2. Konvertiere eventuellen Java-Code in Kotlin und schreibe Kommentare/UI-Texte auf Deutsch.
3. Verbinde die Logik sauber mit der Jetpack Compose UI bzw. der termux-view.
4. Verknüpfe die Terminal-Session-Logik mit dem neuen :core:exec Modul, um Prozesse zu starten.
5. Entferne veraltete Importe und richte Hilt für die Dependency Injection in diesen Klassen ein.
6. Stelle sicher, dass der Code kompilierbar ist (keine ungelösten Referenzen).