Aider Prompt: Utility-Refactoring & Integration
Du bist ein Senior Android Entwickler. Deine Aufgabe ist es, das gesamte Projekt zu analysieren und manuell implementierte Datei-, JSON- oder Archiv-Logik durch die zentralisierten Utility-Klassen in :core:utils zu ersetzen.
Ziel-Klassen in :core:utils:
1. FileUtils: Nutze diese für alle Datei- und Ordner-Operationen (Erstellen, Löschen, Verschieben, Kopieren, Listung, chmod, Lesen/Schreiben von Text).
2. JsonUtils: Nutze diese für alle JSON-Serialisierungen und Datei-Speicherungen von Objekten (basiert auf Kotlinx Serialization).
3. ArchiveUtils: Nutze diese für das Entpacken (Zip, 7z, Tar, Gz) und Komprimieren von Projekten oder Rootfs-Bestandteilen.
Deine Aufgaben:
1. Analyse & Identifikation
* Scanne alle Module (insbesondere :app, :core:data, :feature:editor, :feature:onboarding).
* Suche nach direkten Aufrufen von java.io.File, java.nio.file.Files, ZipInputStream, oder manuellen String-Konvertierungen zu JSON.
* Identifiziere Stellen, an denen File.writeText(), File.readText() oder FileInputStream / FileOutputStream verwendet werden.
2. Refactoring
* Ersetze diese manuellen Implementierungen durch die entsprechenden Methoden aus FileUtils, JsonUtils oder ArchiveUtils.
* Beispiel File-IO: Ersetze if (!file.exists()) file.createNewFile() durch FileUtils.createFile(path).
* Beispiel JSON: Ersetze manuelle JSON-Speicherung durch JsonUtils.saveToFile(data, path).
* Beispiel Archiv: Ersetze Entpackungs-Logik im Onboarding durch ArchiveUtils.extract(archivePath, destDir).
3. Abhängigkeiten (build.gradle.kts)
* Wenn du in einem Modul (z.B. :feature:onboarding) eine Utility-Klasse verwendest, stelle sicher, dass die Abhängigkeit implementation(project(":core:utils")) in der jeweiligen build.gradle.kts eingetragen ist.
* Stelle sicher, dass die notwendigen Imports korrekt gesetzt sind.
4. Konsistenzprüfung
* Achte darauf, dass die Logik (z.B. Fehlerbehandlung) erhalten bleibt, aber der Code durch die Utilities sauberer und wartbarer wird.
* Entferne alle nicht mehr benötigten Hilfsklassen oder privaten Datei-Funktionen innerhalb der Feature-Module, die nun durch :core:utils abgedeckt sind.
Constraints:
* Programmiere ausschließlich in Kotlin.
* Ändere nicht die Funktionsweise der App, sondern nur die interne Implementierung (Refactoring).
* Nutze für alle Datei-Operationen die Pfade aus MCSConstants, sofern zutreffend.