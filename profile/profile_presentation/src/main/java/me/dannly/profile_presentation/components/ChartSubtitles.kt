package me.dannly.profile_presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import me.dannly.core_ui.util.*
import kotlin.random.Random

data class ChartElement(val text: String, val color: Color, val value: Float)

@Composable
private fun ChartSubtitle(
    color: Color,
    text: String,
    subtitleSquareSize: Dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .size(subtitleSquareSize)
    ) {
        Canvas(modifier = Modifier.size(15.dp)) {
            drawRect(color = color, size = size)
        }
        Text(text = text)
    }
}

fun differentColorsList(listSize: Int): List<Color> {
    val list = mutableListOf<Color>()
    while (list.size < listSize) {
        val newColor = Color(Random.nextFloat(), Random.nextFloat(), Random.nextFloat(), 1f)
        if (!list.contains(newColor))
            list.add(newColor)
    }
    return list
}

@OptIn(
    ExperimentalFoundationApi::class
)
@Composable
fun <T> ChartWithSubtitles(
    subtitleBoxSize: Dp = 60.dp,
    elements: List<T>,
    color: (T) -> Color,
    text: (T) -> String,
    portraitChart: @Composable (ColumnScope.(List<T>) -> Unit)? = null,
    landscapeChart: @Composable (RowScope.(List<T>) -> Unit)? = null
) {
    val gridCells = mediaQuery(
        Dimensions.DimensionType.WIDTH inSize WindowSize.COMPACT to 3,
        Dimensions.DimensionType.WIDTH inSize WindowSize.MEDIUM to 4,
        Dimensions.DimensionType.WIDTH inSize WindowSize.EXPANDED to 5
    )
    WithOrientation(portrait = {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            portraitChart?.invoke(this, elements)
            LazyVerticalGrid(
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.SpaceEvenly,
                cells = GridCells.Fixed(gridCells),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                items(elements) {
                    ChartSubtitle(
                        color = color(it),
                        text = text(it),
                        subtitleSquareSize = subtitleBoxSize
                    )
                }
            }
        }
    }) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
        ) {
            landscapeChart?.invoke(this, elements)
            LazyVerticalGrid(
                horizontalArrangement = Arrangement.SpaceEvenly, cells = GridCells.Fixed(
                    gridCells
                ), verticalArrangement = Arrangement.Center, modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxHeight()
                    .align(Alignment.CenterVertically)
            ) {
                items(elements) {
                    ChartSubtitle(
                        color = color(it),
                        text = text(it),
                        subtitleSquareSize = subtitleBoxSize
                    )
                }
            }
        }
    }
}