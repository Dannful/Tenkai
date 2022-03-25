package me.dannly.home_presentation.settings.general.components

import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.dannly.core.R
import me.dannly.core.domain.preferences.Preferences

@Composable
fun ListSettings(preferences: Preferences) {
    val scope = rememberCoroutineScope()
    val isGrid = preferences.getIsGrid().collectAsState(initial = false, Dispatchers.IO).value ?: false
    SwitchSetting(
        title = stringResource(id = R.string.anime_list), description = stringResource(
            id = if (isGrid) R.string.preference_grid_anime_list else R.string.preference_not_grid_anime_list
        ), checked = isGrid, icon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_view_list_24),
                contentDescription = null
            )
        }, onCheckedChange = {
            scope.launch {
                preferences.setIsGrid(it)
            }
        }
    )
}