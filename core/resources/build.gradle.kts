plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.mobilecodespace.core.resources"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }
}
