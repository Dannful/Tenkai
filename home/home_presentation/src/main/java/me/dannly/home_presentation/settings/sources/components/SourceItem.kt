package me.dannly.home_presentation.settings.sources.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import me.dannly.core.domain.local.model.SourcedFavourite

@OptIn(
    ExperimentalComposeUiApi::class
)
@Composable
fun SourceItem(favouriteWithSource: SourcedFavourite) {
    var contentVisible by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier
        .clickable { contentVisible = !contentVisible }
        .animateContentSize()) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = favouriteWithSource.favourite.name,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            SourceActionButtons(favouriteWithSource)
        }
        if (contentVisible) {
            Text(
                text = favouriteWithSource.favourite.url,
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}