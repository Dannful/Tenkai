package me.dannly.core_ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavigation = compositionLocalOf<NavHostController> {
    throw Throwable()
}