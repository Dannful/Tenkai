package me.dannly.core_ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import me.dannly.core_ui.theme.LocalSpacing
import me.dannly.core_ui.util.toPx
import kotlin.math.roundToInt

private const val GRID_RATIO = 1.60340633f
val GRID_CELL_WIDTH = 110.dp
val GRID_CELL_HEIGHT = GRID_CELL_WIDTH * GRID_RATIO

@Composable
fun GridImageCard(
    modifier: Modifier = Modifier,
    columnModifier: Modifier = Modifier,
    imageURL: String?,
    topLeft: @Composable () -> Unit = {},
    bottomLeft: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    val spacing = LocalSpacing.current
    val onSurface = MaterialTheme.colors.onSurface
    val cardShape = MaterialTheme.shapes.medium
    val density = LocalDensity.current
    val imageHeightFraction = 0.85f
    Card(modifier = modifier.fillMaxSize()) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.7f)
        ) {
            Column(modifier = columnModifier.matchParentSize()) {
                val maxWidthPx: Float = this@BoxWithConstraints.maxWidth.toPx()
                val maxHeightPx: Float =
                    this@BoxWithConstraints.maxHeight.times(imageHeightFraction).toPx()
                val imageShape = Size(
                    maxWidthPx,
                    maxHeightPx
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(imageHeightFraction)
                ) {
                    CoilImage(
                        shimmerParams = ShimmerParams(
                            baseColor = MaterialTheme.colors.background,
                            highlightColor = MaterialTheme.colors.onBackground
                        ),
                        imageRequest = ImageRequest.Builder(LocalContext.current)
                            .data(imageURL)
                            .transformations(
                                listOf(
                                    RoundedCornersTransformation(
                                        topLeft = cardShape.topStart.toPx(
                                            imageShape, density
                                        ), topRight = cardShape.topEnd.toPx(
                                            imageShape, density
                                        )
                                    )
                                )
                            )
                            .size(
                                maxWidthPx.roundToInt(),
                                maxHeightPx.roundToInt()
                            ).crossfade(true)
                            .scale(Scale.FIT)
                            .build(),
                        modifier = Modifier
                            .fillMaxSize()
                            .drawWithContent {
                                drawContent()
                                val height = size.height * 0.5f
                                drawRoundRect(
                                    brush = Brush.verticalGradient(
                                        0f to onSurface,
                                        1f to Color.Transparent,
                                        startY = 0f,
                                        endY = height
                                    ),
                                    size = Size(width = size.width, height = height),
                                    cornerRadius = CornerRadius(
                                        cardShape.topStart.toPx(
                                            size,
                                            density
                                        )
                                    )
                                )
                            }
                    )
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(
                                bottom = spacing.spaceSmall,
                                start = spacing.spaceSmall
                            )
                    ) {
                        bottomLeft()
                    }
                }
                Box(
                    Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
            ) {
                topLeft()
            }
        }
    }
}


@Composable
fun GridImageCard2(
    modifier: Modifier = Modifier,
    columnModifier: Modifier = Modifier,
    imageURL: String?,
    topLeft: @Composable () -> Unit = {},
    bottomLeft: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    val spacing = LocalSpacing.current
    val onSurface = MaterialTheme.colors.onSurface
    val cardShape = MaterialTheme.shapes.medium
    val density = LocalDensity.current
    Card(modifier = modifier.size(GRID_CELL_WIDTH, GRID_CELL_HEIGHT)) {
        Column(
            verticalArrangement = Arrangement.spacedBy(spacing.spaceExtraSmall),
            modifier = columnModifier.fillMaxSize()
        ) {
            CoilImage(
                shimmerParams = ShimmerParams(
                    baseColor = MaterialTheme.colors.background,
                    highlightColor = MaterialTheme.colors.onBackground
                ),
                imageRequest = ImageRequest.Builder(LocalContext.current)
                    .data(imageURL)
                    .transformations(
                        listOf(
                            RoundedCornersTransformation(
                                topLeft = cardShape.topStart.toPx(
                                    Size(
                                        GRID_CELL_WIDTH.toPx(), GRID_CELL_HEIGHT.toPx() * 0.7f
                                    ), density
                                ), topRight = cardShape.topEnd.toPx(
                                    Size(
                                        GRID_CELL_WIDTH.toPx(), GRID_CELL_HEIGHT.toPx() * 0.7f
                                    ), density
                                )
                            )
                        )
                    )
                    .size(
                        GRID_CELL_WIDTH.toPx().roundToInt(),
                        GRID_CELL_HEIGHT.times(0.7f).toPx().roundToInt()
                    )
                    .build(),
                modifier = Modifier
                    .size(GRID_CELL_WIDTH, GRID_CELL_HEIGHT * 0.7f)
                    .drawWithContent {
                        drawContent()
                        val height = size.height * 0.5f
                        drawRoundRect(
                            brush = Brush.verticalGradient(
                                0f to onSurface,
                                1f to Color.Transparent,
                                startY = 0f,
                                endY = height
                            ),
                            size = Size(width = size.width, height = height),
                            cornerRadius = CornerRadius(cardShape.topStart.toPx(size, density))
                        )
                    }
            )
            Box(
                Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
    Box(
        modifier = Modifier.size(GRID_CELL_WIDTH, GRID_CELL_HEIGHT),
        contentAlignment = Alignment.TopStart
    ) {
        topLeft()
    }
    Box(
        modifier = Modifier
            .size(GRID_CELL_WIDTH, GRID_CELL_HEIGHT * 0.7f)
            .padding(start = spacing.spaceExtraSmall, bottom = spacing.spaceExtraSmall),
        contentAlignment = Alignment.BottomStart
    ) {
        bottomLeft()
    }
}