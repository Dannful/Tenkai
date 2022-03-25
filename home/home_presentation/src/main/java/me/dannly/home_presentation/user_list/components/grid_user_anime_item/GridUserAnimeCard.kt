package me.dannly.home_presentation.user_list.components.grid_user_anime_item

import android.graphics.Typeface
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.dannly.core.domain.remote.model.UserAnimeStatus
import me.dannly.core_ui.components.GridImageCard
import me.dannly.core_ui.components.StrokedText
import me.dannly.core_ui.components.anime.AnimeScore
import me.dannly.core_ui.components.anime.AnimeTitle
import me.dannly.core_ui.theme.LocalSpacing
import me.dannly.home_domain.model.CachedUserAnime
import me.dannly.core_ui.components.resourceMediaListStatuses
import me.dannly.home_presentation.user_list.util.formatEpisodeTime

@Composable
fun GridUserAnimeCard(
    modifier: Modifier = Modifier,
    columnModifier: Modifier = Modifier,
    cachedUserAnime: CachedUserAnime?
) {
    val spacing = LocalSpacing.current
    GridImageCard(
        columnModifier = columnModifier,
        modifier = modifier,
        imageURL = cachedUserAnime?.cachedAnime?.coverUrl,
        topLeft = {
            if (cachedUserAnime?.score?.takeUnless { it == 0.toDouble() } != null)
                AnimeScore(averageScore = cachedUserAnime.score)
        },
        bottomLeft = {
            Column(verticalArrangement = Arrangement.spacedBy(spacing.spaceExtraSmall)) {
                val typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
                if (cachedUserAnime?.status != null) {
                    StrokedText(
                        text = if (cachedUserAnime.status == UserAnimeStatus.CURRENT) "${cachedUserAnime.progress}${
                            buildString {
                                if (cachedUserAnime.cachedAnime.nextEpisode != null)
                                    append("/${(cachedUserAnime.cachedAnime.nextEpisode ?: 1) - 1}")
                                if (cachedUserAnime.cachedAnime.episodes != null)
                                    append("/${cachedUserAnime.cachedAnime.episodes}")
                            }
                        }" else resourceMediaListStatuses[cachedUserAnime.status.ordinal],
                        typeface = typeface
                    )
                }
                if (cachedUserAnime?.cachedAnime?.timeUntilNextEpisode != null)
                    StrokedText(
                        text = formatEpisodeTime(cachedUserAnime.cachedAnime.timeUntilNextEpisode?.toLong() ?: 0),
                        typeface = typeface
                    )
            }
        }) {
        AnimeTitle(title = cachedUserAnime?.cachedAnime?.title)
    }
}