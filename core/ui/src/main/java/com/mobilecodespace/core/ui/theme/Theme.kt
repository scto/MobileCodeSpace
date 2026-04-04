package com.mobilecodespace.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColors = darkColorScheme(
    primary = AccentPurple,
    background = DarkPurple,
    surface = SecondaryPurple,
    onPrimary = White,
    onBackground = White,
    onSurface = White
)

@Composable
fun MCSTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColors,
        typography = Typography,
        content = content
    )
}
