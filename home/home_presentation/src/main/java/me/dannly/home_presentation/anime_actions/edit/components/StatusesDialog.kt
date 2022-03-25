package me.dannly.home_presentation.anime_actions.edit.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.AlertDialog
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties
import me.dannly.core_ui.components.resourceMediaListStatuses

@Composable
fun StatusesDialog(
    current: String?,
    onSelectedChange: (String?) -> Unit
) {
    AlertDialog(
        onDismissRequest = { onSelectedChange(null) },
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true),
        buttons = {
            resourceMediaListStatuses.forEach { status ->
                Row(
                    Modifier
                    .fillMaxWidth()
                    .selectableGroup()
                    .selectable(
                        selected = (current == status),
                        onClick = { onSelectedChange(status) }
                    )) {
                    RadioButton(
                        selected = current == status,
                        onClick = {
                            onSelectedChange(status)
                        })
                    Text(
                        text = status,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        })
}