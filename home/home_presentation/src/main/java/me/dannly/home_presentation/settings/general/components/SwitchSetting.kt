package me.dannly.home_presentation.settings.general.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.dannly.core_ui.theme.LocalSpacing

@Composable
fun SwitchSetting(
    title: String,
    description: String,
    checked: Boolean,
    icon: @Composable () -> Unit,
    onCheckedChange: (Boolean) -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCheckedChange(!checked)
            }
            .padding(spacing.spaceExtraSmall), horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon()
        Spacer(modifier = Modifier.width(spacing.spaceSmall))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.h5)
            Text(text = description, style = MaterialTheme.typography.body2)
        }
        Spacer(modifier = Modifier.width(spacing.spaceSmall))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}