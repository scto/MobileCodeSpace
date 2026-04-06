Aider Prompt: Implementierung des Onboarding-Download-Systems
Wir stellen den Installationsprozess von lokalen Assets auf einen Laufzeit-Download (Option 2) um, um das GitHub-Limit zu umgehen und die APK-Größe zu minimieren.
Aufgaben:
1. Abhängigkeiten prüfen (:feature:onboarding)
Stelle sicher, dass die build.gradle.kts im Modul :feature:onboarding folgende Abhängigkeiten hat:
* implementation(project(":core:utils"))
* implementation(libs.androidx.lifecycle.viewmodel.compose)
* implementation(libs.hilt.android)
* kapt(libs.hilt.compiler)
2. ViewModel Implementierung
Erstelle oder aktualisiere OnboardingViewModel.kt im Modul :feature:onboarding:
* Implementiere einen OnboardingState (Sealed Class) mit: Idle, Downloading(progress: Int), Extracting, Completed, Error(message: String).
* Erstelle eine Funktion startInstallation(targetDir: String).
* Implementiere eine downloadFile Funktion mit Coroutines (Dispatchers.IO), die einen BufferedInputStream nutzt und den Fortschritt in Prozent berechnet.
* Nutze nach dem Download ArchiveUtils.unzip, um die Datei in das Zielverzeichnis zu entpacken.
* Nutze FileUtils.deleteFile, um das temporäre Archiv nach dem Entpacken zu löschen.
* Verwende als Platzhalter-URL: https://github.com/scto/MobileCodeSpace/releases/download/v1.0/cmdline.zip.
3. UI Implementierung (Compose)
Erstelle OnboardingScreen.kt im Modul :feature:onboarding:
* Nutze collectAsStateWithLifecycle(), um den State vom ViewModel zu beobachten.
* Zeige je nach State unterschiedliche UI-Elemente:
   * Idle: Einen "Installation starten" Button.
   * Downloading: Einen LinearProgressIndicator mit der Prozentanzeige und dem Text "Lade Tools herunter...".
   * Extracting: Einen unbestimmten Progress-Indicator mit dem Text "Entpacke Systemkomponenten...".
   * Completed: Eine Erfolgsmeldung und einen Button "Zum Dashboard", der die Navigation auslöst.
   * Error: Die Fehlermeldung in Rot und einen "Wiederholen" Button.
* Das Design soll dem Dark Purple Theme (#2D1B4E) entsprechen.
4. Bereinigung
* Entferne Code-Fragmente, die versuchen, cmdline.zip direkt aus den App-Assets zu laden.
* Stelle sicher, dass die OnboardingActivity (oder der NavHost) den neuen Screen korrekt lädt.
Benutze für alle Texte die deutschen Sprachressourcen aus :core:resources. Falls dort Einträge fehlen, erstelle sie.