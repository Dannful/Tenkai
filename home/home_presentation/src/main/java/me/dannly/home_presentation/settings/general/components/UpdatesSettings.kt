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
fun UpdatesSettings(
    preferences: Preferences
) {
    val scope = rememberCoroutineScope()
    val shouldReturnToMainScreen = preferences.getShouldReturnToMainScreen().collectAsState(
        initial = true, Dispatchers.IO
    ).value ?: true
    SwitchSetting(
        title = stringResource(id = R.string.updates),
        description = stringResource(id = if (shouldReturnToMainScreen) R.string.preference_return_to_main_screen else R.string.preference_not_return_to_main_screen),
        checked = shouldReturnToMainScreen,
        icon = {
            Icon(
                contentDescription = null,
                painter = painterResource(id = R.drawable.ic_baseline_update_24)
            )
        },
        onCheckedChange = {
            scope.launch {
                preferences.setShouldReturnToMainScreen(it)
            }
        }
    )
}