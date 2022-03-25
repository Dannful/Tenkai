package me.dannly.tenkai.components.toolbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable

@Composable
fun appToolbar(
    drawerOpen: (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null
) = @Composable {
    TopAppBar(
        title = title ?: defaultToolbarTitle,
        navigationIcon = icon ?: getDefaultToolbarIcon(drawerOpen ?: {}),
        actions = actions ?: {}
    )
}