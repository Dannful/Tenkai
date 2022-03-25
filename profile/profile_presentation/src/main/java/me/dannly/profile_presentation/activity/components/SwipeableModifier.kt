package me.dannly.profile_presentation.activity.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.offset
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.material.swipeable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.IntOffset
import me.dannly.core_ui.util.screenWidth
import me.dannly.core_ui.util.toPx
import me.dannly.profile_presentation.activity.util.SwipeDirection
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
fun Modifier.getSwipeableModifier(swipeableState: SwipeableState<SwipeDirection>): Modifier =
    composed {
        this
            .swipeable(
                state = swipeableState,
                orientation = Orientation.Horizontal,
                anchors = mapOf(
                    0f to SwipeDirection.CENTER,
                    screenWidth(0.75f).toPx() to SwipeDirection.RIGHT
                )
            )
            .offset {
                IntOffset(x = swipeableState.offset.value.roundToInt(), y = 0)
            }
    }