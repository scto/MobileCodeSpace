package com.mobilecodespace.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary = PurpleAccent,
    secondary = PurpleSecondary,
    background = PurpleBase,
    surface = PurpleSecondary,
    onPrimary = White,
    onSecondary = White,
    onBackground = White,
    onSurface = White
)

private val LightColors = lightColorScheme(
    primary = PurpleBase,
    secondary = PurpleAccent,
    background = White,
    surface = White,
    onPrimary = White,
    onSecondary = PurpleBase,
    onBackground = PurpleBase,
    onSurface = PurpleBase
)

@Composable
fun MCSTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
