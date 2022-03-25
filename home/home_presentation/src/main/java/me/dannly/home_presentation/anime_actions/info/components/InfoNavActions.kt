package me.dannly.home_presentation.anime_actions.info.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import me.dannly.core_ui.components.toolbar.toolbarActions
import me.dannly.core.R

val animeInfoNavActions: @Composable RowScope.() -> Unit
    get() = toolbarActions {
        var visible by remember {
            mutableStateOf(false)
        }
        InfoTitlesDialog(visible = visible) {
            visible = false
        }
        IconButton(
            onClick = {
                visible = true
            }
        ) {
            Icon(
                contentDescription = stringResource(R.string.more),
                imageVector = Icons.Filled.ArrowDropDown
            )
        }
    }