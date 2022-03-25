package me.dannly.core_ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage
import me.dannly.core.R
import me.dannly.core_ui.theme.LocalSpacing
import me.dannly.core_ui.util.toPx
import kotlin.math.roundToInt

@Composable
inline fun ImageCard(
    modifier: Modifier = Modifier,
    rowModifier: Modifier = Modifier,
    imageURL: String?,
    title: String?,
    crossinline content: @Composable ColumnScope.() -> Unit
) {
    val spacing = LocalSpacing.current
    val ratio = 0.705521472f
    val cardHeight = 110.dp
    val imageWidth = (cardHeight.value * ratio).dp
    Card(
        modifier = modifier
            .height(cardHeight)
            .fillMaxWidth()
    ) {
        Row(
            modifier = rowModifier
                .fillMaxSize(), horizontalArrangement =
            Arrangement.spacedBy(spacing.spaceExtraSmall)
        ) {
            CoilImage(
                shimmerParams = ShimmerParams(
                    baseColor = MaterialTheme.colors.background,
                    highlightColor = MaterialTheme.colors.onBackground
                ),
                imageRequest = ImageRequest.Builder(LocalContext.current)
                    .size(imageWidth.toPx().roundToInt(), cardHeight.toPx().roundToInt())
                    .error(R.drawable.tenkai_round)
                    .data(imageURL)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(imageWidth, cardHeight)
                    .clip(RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement =
                Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(spacing.spaceExtraSmall)
            ) {
                if (title != null)
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.body1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Divider()
                    }
                content(this)
            }
        }
    }
}