package me.dannly.core_ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.vector.ImageVector
import me.dannly.core.util.UiText

data class RouteItem(
    val imageVector: ImageVector? = null,
    @DrawableRes val drawable: Int? = null,
    val title: UiText,
    val position: Int
)
