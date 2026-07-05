package com.tejashree.codereviewlab.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = InboxAccentDark,
    onPrimary = InboxBackgroundDark,
    secondary = InboxSecondaryDark,
    onSecondary = InboxPrimaryDark,
    tertiary = InboxAccentDark,
    onTertiary = InboxBackgroundDark,
    surface = InboxSurfaceDark,
    onSurface = InboxPrimaryDark,
    background = InboxBackgroundDark,
    onBackground = InboxPrimaryDark,
    primaryContainer = Color(0xFF0C4A6E), // Sky 900
    onPrimaryContainer = Color(0xFFE0F2FE), // Sky 100
    secondaryContainer = Color(0xFF1E293B), // Slate 800
    onSecondaryContainer = InboxSecondaryDark,
    surfaceVariant = Color(0xFF1E293B), // Slate 800 - Professional Gray Card
    onSurfaceVariant = InboxPrimaryDark,
    error = InboxErrorDark,
    onError = InboxBackgroundDark,
    errorContainer = Color(0xFF7F1D1D), // Red 900
    onErrorContainer = InboxErrorDark
)

private val LightColorScheme = lightColorScheme(
    primary = InboxPrimary,
    onPrimary = Color.White,
    secondary = InboxSecondary,
    onSecondary = Color.White,
    tertiary = InboxAccent,
    onTertiary = Color.White,
    surface = InboxSurface,
    onSurface = InboxPrimary,
    background = Color.White, // Pure white background for gray cards to pop
    onBackground = InboxPrimary,
    primaryContainer = Color(0xFFE0F2FE), // Sky 100 for soft but visible containers
    onPrimaryContainer = Color(0xFF0369A1), // Sky 700
    secondaryContainer = Color(0xFFF1F5F9), // Slate 100 for secondary elements
    onSecondaryContainer = InboxSecondary,
    surfaceVariant = Color(0xFFF8FAFC), // Slate 50 - Very subtle Gray
    onSurfaceVariant = InboxPrimary,
    error = InboxError,
    onError = Color.White,
    errorContainer = Color(0xFFFEE2E2), // Red 100
    onErrorContainer = InboxError,

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun CodeReviewLabTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}