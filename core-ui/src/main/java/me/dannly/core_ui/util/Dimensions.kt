package me.dannly.core_ui.util

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

sealed class Dimensions {

    enum class DimensionType {
        WIDTH, HEIGHT
    }

    sealed class DimensionOperator {
        data class In(val range: ClosedRange<Dp>) : DimensionOperator()
    }

    class DimensionComparator(
        val dimensionOperator: DimensionOperator,
        private val dimensionType: DimensionType,
    ) {
        fun compare(screenWidth: Dp, screenHeight: Dp): Boolean {
            return when (dimensionType) {
                DimensionType.HEIGHT -> when (dimensionOperator) {
                    is DimensionOperator.In -> screenHeight in dimensionOperator.range
                }
                DimensionType.WIDTH -> when (dimensionOperator) {
                    is DimensionOperator.In -> screenWidth in dimensionOperator.range
                }
            }
        }
    }
}

enum class WindowSize(val values: Map<Dimensions.DimensionType, ClosedRange<Dp>>) {
    COMPACT(
        mapOf(
            Dimensions.DimensionType.WIDTH to 0.dp..600.dp,
            Dimensions.DimensionType.HEIGHT to 0.dp..480.dp
        )
    ),
    MEDIUM(
        mapOf(
            Dimensions.DimensionType.WIDTH to 600.dp..840.dp,
            Dimensions.DimensionType.HEIGHT to 480.dp..900.dp
        )
    ),
    EXPANDED(
        mapOf(
            Dimensions.DimensionType.WIDTH to 840.dp..Int.MAX_VALUE.dp,
            Dimensions.DimensionType.HEIGHT to 900.dp..Int.MAX_VALUE.dp
        )
    )
}

@Composable
fun MediaQuery(comparator: Dimensions.DimensionComparator, content: @Composable () -> Unit) {
    val current = LocalConfiguration.current
    if (comparator.compare(current.screenWidthDp.dp, current.screenHeightDp.dp))
        content()
}

@Composable
fun <T> mediaQuery(vararg values: Pair<Dimensions.DimensionComparator, T>): T {
    val current = LocalConfiguration.current
    values.find {
        it.first.compare(
            current.screenWidthDp.dp,
            current.screenHeightDp.dp
        )
    }?.let { return it.second }
    return values[0].second
}

@Composable
fun WithOrientation(portrait: @Composable () -> Unit, landscape: @Composable () -> Unit) {
    val current = LocalConfiguration.current
    if (current.orientation == Configuration.ORIENTATION_PORTRAIT) {
        portrait()
    } else {
        landscape()
    }
}

@Composable
fun <T> withOrientation(portrait: T, landscape: T): T {
    val current = LocalConfiguration.current
    return if (current.orientation == Configuration.ORIENTATION_PORTRAIT) {
        portrait
    } else {
        landscape
    }
}

@Composable
fun screenWidth(fraction: Float = 1f) = LocalConfiguration.current.screenWidthDp.dp * fraction

@Composable
fun screenHeight(fraction: Float = 1f) = LocalConfiguration.current.screenHeightDp.dp * fraction

@Composable
fun Int.toDp() = with(LocalDensity.current) { toDp() }

@Composable
fun TextUnit.toPx() = with(LocalDensity.current) { toPx() }

@Composable
fun Dp.toPx() : Float = with(LocalDensity.current) { toPx() }

@Composable
fun Float.toDp() : Dp = with(LocalDensity.current) { toDp() }

infix fun Dimensions.DimensionType.inSize(windowSize: WindowSize): Dimensions.DimensionComparator {
    return Dimensions.DimensionComparator(
        dimensionOperator = Dimensions.DimensionOperator.In(windowSize.values[this]!!),
        dimensionType = this
    )
}