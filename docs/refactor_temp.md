Aider Prompt: Refactoring des temp-Ordners & Konstanten-Migration
Du bist ein Senior Android Software Architekt und arbeitest am Projekt "MobileCodeSpace" (MCS). Deine aktuelle Aufgabe besteht darin, Legacy-Code aus einem temp-Ordner in die saubere Multi-Modul-Architektur zu migrieren und eine projektweite Konstanten-Anpassung vorzunehmen.
AUFGABE 1: Globale Konstanten-Migration (MCSConstants)
Die alte Konstanten-Datei com.mcs.app.MCSConstants wurde gelöscht. Es existiert nun eine neue, erweiterte Version unter com.mobilecodespace.app.MCSConstants. Diese neue Datei enthält auch die korrekten URLs für Ubuntu, PRoot, Rootfs und die Libraries (Libs).
Aktionen:
1. Durchsuche das gesamte Projekt nach dem Import com.mcs.app.MCSConstants und ersetze ihn durch com.mobilecodespace.app.MCSConstants.
2. Passe alle Klassen, die bisher hartkodierte Download-URLs verwendet haben (wie z.B. der PRootManager oder OnboardingViewModel), so an, dass sie nun die URLs aus der neuen MCSConstants Datei abrufen.
3. Stelle sicher, dass keine Build-Fehler durch fehlende Konstanten entstehen.
AUFGABE 2: Neues Submodul :core:exec erstellen & befüllen
Der Basis-Ordner temp enthält Logik zur Ausführung von Shell-Befehlen, PRoot-Umgebungen und Prozess-Verwaltung. Diese Logik muss in ein neues Modul.
Aktionen:
1. Erstelle das Submodul :core:exec (inklusive build.gradle.kts mit dem Namespace com.mobilecodespace.core.exec).
2. Trage :core:exec in die settings.gradle.kts ein.
3. Analysiere die Dateien im Hauptverzeichnis des temp-Ordners (alles außer temp/terminal).
4. Refaktoriere diese Dateien in das :core:exec Modul:
   * Behalte die vollständige Funktionalität bei.
   * Passe den Code an moderne Kotlin-Standards (Coroutines, Flow) und die MobileCodeSpace-Architektur an.
   * Nutze für alle Pfad-Auflösungen zwingend die Environment.kt aus :core:utils.
   * Nutze für alle Dateioperationen die FileUtils.kt aus :core:utils.
5. Stelle sicher, dass :core:exec als Abhängigkeit in Modulen verfügbar gemacht wird, die Prozesse ausführen müssen (z.B. :core:data oder :feature:terminal).
AUFGABE 3: Refactoring in :feature:terminal
Der Ordner temp/terminal/ enthält spezifische UI- oder Session-Logik für das Terminal.
Aktionen:
1. Analysiere die Dateien im Ordner temp/terminal/.
2. Migriere diese Dateien in das bestehende Modul :feature:terminal.
3. Passe auch hier die Architektur an:
   * Verbinde die Logik sauber mit der Jetpack Compose UI (falls zutreffend) oder der termux-view.
   * Verknüpfe die Terminal-Session-Logik mit dem neuen :core:exec Modul, falls Prozesse gestartet werden müssen.
   * Entferne veraltete Importe und nutze Hilt für Dependency Injection.
REGELN FÜR DAS REFACTORING
* Kein Funktionsverlust: Die Logik aus dem temp-Ordner darf modernisiert werden, aber keine Kernfunktionen (wie Argument-Parsing, Env-Setup für PRoot) dürfen verloren gehen.
* Sprache: Alle neuen Kommentare und UI-Texte MÜSSEN auf DEUTSCH verfasst werden.
* Kotlin Only: Konvertiere eventuell verbliebenen Java-Code während des Refactorings in idiomatisches Kotlin.
* Aufwand: Gehe systematisch vor. Repariere nach der Verschiebung alle Abhängigkeiten, bis ./gradlew assembleDebug fehlerfrei durchläuft.