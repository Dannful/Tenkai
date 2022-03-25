package me.dannly.tenkaiapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import me.dannly.core_ui.Purple200
import me.dannly.core_ui.Purple500
import me.dannly.core_ui.Purple700
import me.dannly.core_ui.Teal200
import me.dannly.core_ui.theme.LocalNavigation
import me.dannly.core_ui.theme.LocalSpacing
import me.dannly.core_ui.theme.Spacing

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun TenkaiAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val navController = rememberNavController()
    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalNavigation provides navController
    ) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}