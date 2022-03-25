package me.dannly.browse_presentation.catalog.components

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import me.dannly.browse_presentation.catalog.util.SortType
import me.dannly.core.R

@Composable
fun SortBox(
    visible: Boolean,
    options: List<String>,
    selectedOption: Int = 0,
    selectedSortType: SortType = SortType.DESCENDING,
    onSelect: (Int, SortType) -> Unit
) {
    var selected by remember {
        mutableStateOf(selectedOption)
    }
    var sortType by remember {
        mutableStateOf(selectedSortType)
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        Column {
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                options.forEachIndexed { index, text ->
                    RadioButtonWithText(text = text, selected = selected == index) {
                        selected = index
                        onSelect(selected, sortType)
                    }
                }
            }
            Row {
                RadioButtonWithText(
                    text = stringResource(id = R.string.ascending),
                    selected = sortType == SortType.ASCENDING
                ) {
                    sortType = SortType.ASCENDING
                    onSelect(selected, sortType)
                }
                RadioButtonWithText(
                    text = stringResource(id = R.string.descending),
                    selected = sortType == SortType.DESCENDING
                ) {
                    sortType = SortType.DESCENDING
                    onSelect(selected, sortType)
                }
            }
        }
    }
}