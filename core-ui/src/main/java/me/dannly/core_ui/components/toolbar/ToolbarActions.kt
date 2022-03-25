package me.dannly.core_ui.components.toolbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

inline fun toolbarActions(crossinline actions: @Composable RowScope.() -> Unit): @Composable RowScope.() -> Unit =
    {
        actions()
    }