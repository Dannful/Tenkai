package me.dannly.home_presentation.user_list.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import me.dannly.core.util.SwipeDirection
import me.dannly.core_ui.util.screenWidth
import me.dannly.core_ui.util.toPx
import me.dannly.home_domain.model.CachedUserAnime
import me.dannly.home_presentation.user_list.components.grid_user_anime_item.GridUserAnimeCard
import me.dannly.home_presentation.user_list.components.user_anime_item.UserAnimeItem
import kotlin.math.roundToInt

private val anchors
    @Composable
    get() = mapOf(
        screenWidth(-0.75f).toPx() to SwipeDirection.LEFT,
        0f to SwipeDirection.INITIAL,
        screenWidth(0.75f).toPx() to SwipeDirection.RIGHT
    )

@OptIn(
    ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun AnimeCardWithEvents(
    cachedUserAnime: CachedUserAnime?,
    swipeState: SwipeableState<SwipeDirection>,
    isGrid: Boolean, onClick: (Int) -> Unit
) {
    val wholeCardModifier = Modifier
        .swipeable(
            orientation = Orientation.Horizontal, state = swipeState,
            anchors = anchors, thresholds = { _, _ -> FractionalThreshold(0.3f) })
        .combinedClickable(enabled = true, onClick = {
            onClick(
                cachedUserAnime?.cachedAnime?.id ?: -1
            )
        })
    val cardContentModifier = Modifier.offset {
        IntOffset(
            swipeState.offset.value.roundToInt(),
            0
        )
    }
    Box(modifier = Modifier.fillMaxWidth()) {
        if (!isGrid)
            UserAnimeItem(
                cachedUserAnime = cachedUserAnime,
                modifier = wholeCardModifier,
                rowModifier = cardContentModifier
            )
        else
            GridUserAnimeCard(
                cachedUserAnime = cachedUserAnime,
                modifier = wholeCardModifier,
                columnModifier = cardContentModifier
            )
    }
}