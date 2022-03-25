package me.dannly.browse_presentation.catalog.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.res.stringResource
import me.dannly.core_ui.components.toolbar.toolbarActions
import me.dannly.core_ui.navigation.Destination
import me.dannly.core_ui.theme.LocalNavigation
import me.dannly.core.R

val browseNavActions
    get() = toolbarActions {
        val controller = LocalNavigation.current
        IconButton(
            onClick = {
                controller.navigate(Destination.Main.Browse.Search.route.toString())
            }
        ) {
            Icon(
                contentDescription = stringResource(id = R.string.search),
                imageVector = Icons.Default.Search
            )
        }
    }