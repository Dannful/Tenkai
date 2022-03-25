package me.dannly.home_presentation.user_list.components.user_anime_item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.dannly.core_ui.components.ImageCard
import me.dannly.home_domain.model.CachedUserAnime

@Composable
fun UserAnimeItem(
    modifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
    cachedUserAnime: CachedUserAnime?
) {
    ImageCard(
        modifier = modifier,
        rowModifier = rowModifier,
        imageURL = cachedUserAnime?.cachedAnime?.coverUrl,
        title = cachedUserAnime?.cachedAnime?.title.orEmpty(),
    ) {
        Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxSize()) {
            Status(cachedUserAnime)
            TimeUntilNextEpisode(cachedUserAnime)
            ProgressText(cachedUserAnime)
            ProgressBar(cachedUserAnime)
        }
    }
}