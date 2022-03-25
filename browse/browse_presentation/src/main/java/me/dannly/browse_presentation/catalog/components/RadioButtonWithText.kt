package me.dannly.browse_presentation.catalog.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.dannly.core_ui.theme.LocalSpacing

@Composable
fun RowScope.RadioButtonWithText(
    text: String,
    selected: Boolean,
    onSelected: () -> Unit
) {
    val spacing = LocalSpacing.current
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing.spaceExtraSmall),
        modifier = Modifier.align(
            Alignment.CenterVertically
        )
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelected,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Text(
            text = text,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}