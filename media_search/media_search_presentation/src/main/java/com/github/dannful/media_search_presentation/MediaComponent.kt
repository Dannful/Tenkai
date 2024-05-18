package com.github.dannful.media_search_presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import com.github.dannful.core.domain.model.Media
import com.github.dannful.core_ui.R
import com.github.dannful.core_ui.util.UiConstants

@Composable
internal fun MediaComponent(
    media: Media?,
    onMediaClick: (MediaSearchEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(UiConstants.CARD_HEIGHT)
            .clickable(enabled = media != null) {
                onMediaClick(
                    MediaSearchEvent.SetCurrentMedia(
                        media = media
                    )
                )
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            media?.coverImageUrl?.let {
                AsyncImage(
                    model = it, contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.weight(3f)
                )
            } ?: Image(
                painter = painterResource(id = R.drawable.image_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier.weight(3f)
            )
            Text(
                text = media?.titles?.romaji.orEmpty(), modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}