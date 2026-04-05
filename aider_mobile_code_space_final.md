Aider Prompt: MobileCodeSpace Professional IDE Setup (v2.2)

Du bist ein Senior Android Software Architekt. Wir bauen "MobileCodeSpace" (MCS), eine hochperformante IDE für Android. Technologie: Kotlin, Jetpack Compose, Multi-Modul-Architektur, Hilt (DI), MVI/MVVM, Flow. Design: Dark Purple Theme (#2D1B4E) gemäß Referenzbildern. Sprache: Deutsche UI-Texte und deutsche Code-Kommentare.

SCHRITT 1 bis 9: (Zusammenfassung der vorherigen Schritte)

* Modul-Struktur: :app, :core:ui, :core:data, :core:di, :core:domain, :core:models, :core:navigation, :core:resources, :feature:home, :feature:onboarding, :feature:editor, :feature:terminal, :feature:fileexplorer, :feature:git, :feature:settings.

* Integration von SoraEditor, PRoot (Ubuntu), Terminal (Termux-View) und LSP.

* Onboarding-Flow für Berechtigungen und Tool-Installation.

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
