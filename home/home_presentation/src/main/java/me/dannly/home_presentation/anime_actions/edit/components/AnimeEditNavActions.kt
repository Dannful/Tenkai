package me.dannly.home_presentation.anime_actions.edit.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import me.dannly.core.R
import me.dannly.core_ui.components.toolbar.toolbarActions

val animeEditNavActions: @Composable RowScope.() -> Unit
    get() = toolbarActions {
        var visible by remember {
            mutableStateOf(false)
        }
        IconButton(
            onClick = {
                visible = true
            }
        ) {
            Icon(
                contentDescription = stringResource(id = R.string.delete),
                imageVector = Icons.Filled.Delete
            )
        }
        AnimeEditDeleteEntry(visible = visible) {
            visible = false
        }
    }