package me.dannly.profile_presentation.components

import android.text.TextPaint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat

@OptIn(
    ExperimentalFoundationApi::class
)
@Composable
fun BarChart(
    modifier: Modifier = Modifier,
    dataTextSize: TextUnit = 15.sp,
    barWidth: Dp = 15.dp,
    linesStrokeWidth: Dp = 1.dp,
    innerPadding: Dp = 15.dp,
    elements: List<ChartElement>
) {
    ChartWithSubtitles(elements = elements.sortedByDescending { it.value },
        color = { it.color },
        text = { it.text },
        portraitChart = {
            Chart(
                modifier = modifier.size(LocalConfiguration.current.screenWidthDp.dp),
                innerPadding = innerPadding,
                linesStrokeWidth = linesStrokeWidth,
                barWidth = barWidth,
                dataTextSize = dataTextSize,
                elements = it
            )
        }) {
        Chart(
            modifier = modifier.size(LocalConfiguration.current.screenWidthDp.div(2).dp),
            innerPadding = innerPadding,
            linesStrokeWidth = linesStrokeWidth,
            barWidth = barWidth,
            dataTextSize = dataTextSize,
            elements = it
        )
    }
}

@Composable
private fun Chart(
    modifier: Modifier,
    innerPadding: Dp,
    linesStrokeWidth: Dp,
    barWidth: Dp,
    dataTextSize: TextUnit,
    elements: List<ChartElement>
) {
    val onSurface = MaterialTheme.colors.onSurface
    val current = LocalDensity.current
    Canvas(modifier = modifier) {
        val padding = innerPadding.toPx()
        drawLine(
            color = onSurface,
            strokeWidth = linesStrokeWidth.toPx(),
            start = Offset(padding, padding),
            end = Offset(padding, size.height - padding)
        )
        drawLine(
            color = onSurface,
            strokeWidth = linesStrokeWidth.toPx(),
            start = Offset(padding, size.height - padding),
            end = Offset(size.width - padding, size.height - padding)
        )
        elements.forEachIndexed { index, barChartElement ->
            elementRect(
                rectangleColor = barChartElement.color,
                textColor = onSurface,
                rectWidth = barWidth.toPx(),
                padding = padding,
                values = elements,
                currentIndex = index,
                dataTextSize = with(current) { dataTextSize.toPx() }
            )
        }
    }
}

private fun DrawScope.elementRect(
    rectangleColor: Color,
    textColor: Color,
    rectWidth: Float = 50f,
    padding: Float,
    values: List<ChartElement>,
    currentIndex: Int,
    dataTextSize: Float
) {
    val currentValue = values[currentIndex].value
    val height =
        (size.height - 2 * padding) * (currentValue / (values.map { it.value }.maxOrNull() ?: 1f))
    val position = Offset(
        x = (2 * padding + (size.width - 3 * padding) / values.size * currentIndex),
        y = size.height - padding - height
    )
    drawRect(
        color = rectangleColor,
        size = Size(
            width = rectWidth,
            height = height
        ),
        topLeft = position
    )
    val middleOfBar = (2 * position.x + rectWidth) / 2
    val paint = TextPaint().apply {
        textAlign = android.graphics.Paint.Align.CENTER
        color = textColor.toArgb()
    }
    drawIntoCanvas {
        it.nativeCanvas.drawText(
            DecimalFormat("#.#").format(currentValue),
            middleOfBar,
            position.y - 10f,
            paint.apply {
                textSize = dataTextSize
            }
        )
    }
}