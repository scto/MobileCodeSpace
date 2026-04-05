plugins {
   alias(libs.plugins.android.application)
   alias(libs.plugins.kotlin.android)
   alias(libs.plugins.hilt)
   kotlin("kapt")
}

android {
   namespace = "com.mobilecodespace.app"
   compileSdk = 34
   
   defaultConfig {
       applicationId = "com.mobilecodespace.app"
       minSdk = 26
       targetSdk = 34
       versionCode = 1
       versionName = "1.0"
   }
}

dependencies {
   implementation(project(":core:ui"))
   implementation(project(":core:di"))
   implementation(project(":core:utils"))
   implementation(project(":core:data"))
   implementation(project(":feature:home"))
   implementation(project(":feature:onboarding"))
   implementation(project(":feature:editor"))
   implementation(project(":feature:terminal"))
   
   implementation(libs.google.hilt.android)
   kapt(libs.google.hilt.compiler)
}
