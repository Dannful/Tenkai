package me.dannly.home_presentation.user_list.components.user_anime_item

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.dannly.core.R
import me.dannly.home_domain.model.CachedUserAnime
import java.text.DecimalFormat

@Composable
fun ProgressText(cachedUserAnime: CachedUserAnime?) {
    Text(
        text = buildString {
            cachedUserAnime?.apply {
                append("${stringResource(id = R.string.progress)}: ")
                append(this.progress)
                if (this.cachedAnime.nextEpisode != null)
                    append("/${(this.cachedAnime.nextEpisode ?: 1) - 1}")
                if (this.cachedAnime.episodes != null)
                    append("/${this.cachedAnime.episodes}")
                if (this.cachedAnime.episodes != null || this.cachedAnime.nextEpisode != null)
                    append(
                        " (${
                            DecimalFormat("#.#").format(
                                this.progress.toDouble() /
                                        (this.cachedAnime.episodes ?: this.cachedAnime.nextEpisode?.minus(
                                            1
                                        ).takeUnless { it == 0 } ?: 1) * 100
                            )
                        }%)"
                    )
            }
        },
        style = MaterialTheme.typography.body2
    )
}