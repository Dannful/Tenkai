package me.dannly.home_presentation.user_list.components.user_anime_item

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import me.dannly.home_domain.model.CachedUserAnime
import me.dannly.home_presentation.user_list.util.formatEpisodeTime

@Composable
fun TimeUntilNextEpisode(cachedUserAnime: CachedUserAnime?) {
    val timeUntilAiring =
        cachedUserAnime?.cachedAnime?.timeUntilNextEpisode
    Text(
        text = if (timeUntilAiring != null) formatEpisodeTime(
            timeUntilAiring.toLong()
        ) else "",
        style = TextStyle(fontSize = 12.sp, fontStyle = FontStyle.Italic)
    )
}