package me.dannly.home_presentation.user_list.components.user_anime_item

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.dannly.home_domain.model.CachedUserAnime

@Composable
fun ProgressBar(cachedUserAnime: CachedUserAnime?) {
    if (cachedUserAnime?.cachedAnime?.episodes != null || cachedUserAnime?.cachedAnime?.nextEpisode != null)
        LinearProgressIndicator(
            progress = cachedUserAnime.progress.toFloat() / (cachedUserAnime.cachedAnime.episodes?.toFloat()
                ?: (cachedUserAnime.cachedAnime.nextEpisode?.minus(1)?.toFloat()
                    .takeUnless { it == 0f }
                    ?: 1f)),
            modifier = Modifier.fillMaxWidth()
        )
    else
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
}