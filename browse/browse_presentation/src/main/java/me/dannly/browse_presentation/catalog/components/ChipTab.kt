package me.dannly.browse_presentation.catalog.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import me.dannly.core_ui.theme.LocalSpacing

@Composable
fun ChipsTab(
    items: List<String>,
    isSelected: (String) -> Boolean,
    initialScrollPosition: Int = 0,
    onScrollChange: (Int) -> Unit,
    onSelected: (String, Boolean) -> Unit
) {
    val spacing = LocalSpacing.current
    val state = rememberScrollState(initialScrollPosition)
    Row(
        modifier = Modifier.horizontalScroll(state),
        horizontalArrangement = Arrangement.spacedBy(spacing.spaceExtraSmall)
    ) {
        items.forEach {
            val color by animateColorAsState(
                targetValue = if (isSelected(it))
                    Color.LightGray else MaterialTheme.colors.primary
            )
            Surface(
                color = color,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(spacing.spaceSmall)
            ) {
                Row(modifier = Modifier
                    .toggleable(
                        value = isSelected(it),
                        onValueChange = { selected ->
                            onSelected(it, selected)
                            onScrollChange(state.value)
                        }
                    )) {
                    Text(
                        text = it, style = MaterialTheme.typography.body1, color = Color.White,
                        modifier = Modifier.padding(spacing.spaceSmall)
                    )
                }
            }
        }
    }
}