plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.mcs.core.resources"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }
}
