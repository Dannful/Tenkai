package me.dannly.home_presentation.settings.general

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.dannly.core.domain.preferences.Preferences
import me.dannly.core_ui.theme.LocalSpacing
import me.dannly.home_presentation.settings.general.components.ListSettings
import me.dannly.home_presentation.settings.general.components.UpdatesSettings

@Composable
fun GeneralSettings(
    preferences: Preferences
) {
    val spacing = LocalSpacing.current
    Column(
        Modifier.padding(vertical = spacing.spaceSmall),
        verticalArrangement = Arrangement.spacedBy(spacing.spaceExtraSmall)
    ) {
        UpdatesSettings(preferences = preferences)
        Divider()
        ListSettings(preferences = preferences)
    }
}