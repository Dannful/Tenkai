package com.github.dannful.home_presentation

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.dannful.core.data.model.QueryInput
import com.github.dannful.core.domain.model.Media
import com.github.dannful.core.domain.model.UserMedia
import com.github.dannful.core.domain.model.UserMediaUpdate
import com.github.dannful.core_ui.R
import com.github.dannful.core_ui.util.UiConstants
import com.github.dannful.core_ui.util.domainStatus
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun UserMediaComponent(
    userMedia: UserMedia?,
    onEvent: (HomeEvent) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(UiConstants.CARD_HEIGHT)
            .clickable(enabled = userMedia != null) {
                onEvent(
                    HomeEvent.ShowDialog(
                        MediaWithUpdate(
                            media = userMedia?.media ?: return@clickable,
                            update = UserMediaUpdate(
                                mediaId = QueryInput.present(userMedia.media.id),
                                progress = QueryInput.present(userMedia.progress),
                                startedAt = QueryInput.presentIfNotNull(userMedia.startedAt),
                                completedAt = QueryInput.presentIfNotNull(userMedia.completedAt),
                                status = QueryInput.present(userMedia.status),
                                score = QueryInput.presentIfNotNull(userMedia.score)
                            )
                        )
                    )
                )
            },
        elevation = CardDefaults.outlinedCardElevation()
    ) {
        val density = LocalDensity.current
        val anchors = remember {
            DraggableAnchors {
                CardSwipe.LEFT at -with(density) { UiConstants.CARD_WIDTH.toPx() }
                CardSwipe.CENTER at 0f
                CardSwipe.RIGHT at with(density) { UiConstants.CARD_WIDTH.toPx() }
            }
        }
        val anchorState = remember {
            AnchoredDraggableState(
                initialValue = CardSwipe.CENTER,
                anchors = anchors,
                positionalThreshold = { distance: Float -> distance / 2 },
                velocityThreshold = { with(density) { UiConstants.CARD_SWIPE_VELOCITY.toPx() } },
                snapAnimationSpec = tween(),
                decayAnimationSpec = exponentialDecay(),
                confirmValueChange = { newValue: CardSwipe ->
                    when (newValue) {
                        CardSwipe.LEFT -> {
                            onEvent(
                                HomeEvent.DecreaseProgress(
                                    userMedia?.media?.id ?: return@AnchoredDraggableState false
                                )
                            )
                            false
                        }

                        CardSwipe.CENTER -> false
                        CardSwipe.RIGHT -> {
                            onEvent(
                                HomeEvent.IncreaseProgress(
                                    userMedia?.media?.id ?: return@AnchoredDraggableState false
                                )
                            )
                            false
                        }
                    }
                },
            )
        }
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .offset {
                    IntOffset(
                        x = anchorState
                            .requireOffset()
                            .roundToInt(),
                        y = 0,
                    )
                }
                .anchoredDraggable(state = anchorState, orientation = Orientation.Horizontal)
        ) {
            userMedia?.media?.coverImageUrl?.let {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            } ?: Image(
                painter = painterResource(id = R.drawable.image_placeholder),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize()
            )
            Text(
                text = userMedia?.title.orEmpty(), style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                Text(
                    text = if (userMedia == null) "" else domainStatus()[userMedia.media.status.ordinal],
                    style = MaterialTheme.typography.labelSmall,
                    fontStyle = FontStyle.Italic
                )
                Text(
                    text = if (userMedia == null) "" else formatTime(
                        userMedia.media.timeUntilNextEpisode ?: 0
                    ),
                    style = MaterialTheme.typography.labelSmall,
                    fontStyle = FontStyle.Italic
                )
                if (userMedia?.media?.status?.isWatchable == true) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${userMedia.progress}/${userMedia.media.nextEpisode ?: userMedia.media.episodes ?: "?"}",
                            style = MaterialTheme.typography.labelSmall
                        )
                        LinearProgressIndicator(progress = {
                            userMedia.progress.toFloat() / (userMedia.media.nextEpisode
                                ?: userMedia.media.episodes ?: userMedia.progress)
                        }, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}

private fun formatTime(time: Int): String {
    var remainingTime = time
    val months = remainingTime / TimeUnit.DAYS.toSeconds(30)
    remainingTime %= TimeUnit.DAYS.toSeconds(30).toInt()

    val days = remainingTime / TimeUnit.DAYS.toSeconds(1)
    remainingTime %= TimeUnit.DAYS.toSeconds(1).toInt()

    val hours = remainingTime / TimeUnit.HOURS.toSeconds(1)
    remainingTime %= TimeUnit.HOURS.toSeconds(1).toInt()

    val minutes = remainingTime / TimeUnit.MINUTES.toSeconds(1)
    remainingTime %= TimeUnit.MINUTES.toSeconds(1).toInt()

    return buildString {
        if (months > 0) append("${months}M ")
        if (days > 0) append("${days}d ")
        if (hours > 0) append("${hours}h ")
        if (minutes > 0) append("${minutes}m ")
        if (remainingTime > 0) append("${remainingTime}s")
    }.trim()
}

private enum class CardSwipe {
    LEFT, CENTER, RIGHT
}

data class MediaWithUpdate(
    val media: Media,
    val update: UserMediaUpdate
)