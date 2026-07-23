package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val HydrationDarkColorScheme = darkColorScheme(
    primary = TurquoisePrimary,
    onPrimary = OnTurquoisePrimary,
    primaryContainer = TurquoisePrimaryContainer,
    onPrimaryContainer = OnTurquoisePrimaryContainer,
    secondary = TealSecondary,
    onSecondary = OnTealSecondary,
    secondaryContainer = TealSecondaryContainer,
    onSecondaryContainer = OnTealSecondaryContainer,
    tertiary = SkyTertiary,
    onTertiary = OnSkyTertiary,
    background = DarkBackground,
    onBackground = TextPrimary,
    surface = DarkSurface,
    onSurface = TextPrimary,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = DarkSurfaceContainerHigh,
    outlineVariant = Color(0xFF1E293B),
    error = ErrorRed
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = HydrationDarkColorScheme,
        typography = Typography,
        content = content
    )
}
