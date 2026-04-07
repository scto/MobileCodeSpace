# SoraEditor
-keep class io.github.rosemoe.sora.** { *; }
-keep interface io.github.rosemoe.sora.** { *; }

# PRoot
-keep class com.mobilecodespace.core.exec.** { *; }

# Hilt/Dagger
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class hilt_aggregated_deps.** { *; }

# Jetpack Compose
-keep class androidx.compose.** { *; }

# Keep all native methods for PRoot
-keepclasseswithmembernames class * {
    native <methods>;
}
