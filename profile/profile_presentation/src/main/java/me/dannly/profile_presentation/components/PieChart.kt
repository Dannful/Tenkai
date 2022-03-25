package me.dannly.profile_presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.text.DecimalFormat

@OptIn(
    ExperimentalFoundationApi::class
)
@Composable
fun PieChart(
    subtitleBoxSize: Dp = 60.dp,
    elements: List<ChartElement>
) {
    ChartWithSubtitles(
        elements = elements.sortedByDescending { it.value },
        subtitleBoxSize = subtitleBoxSize,
        color = { it.color }, text = { "${it.text} ${DecimalFormat("#.#").format(it.value * 100)}%" },
        portraitChart = {
            Pie(
                Modifier
                    .size(LocalConfiguration.current.screenWidthDp.div(2).dp)
                    .align(Alignment.CenterHorizontally), it
            )
        }
    ) {
        Pie(Modifier.size(LocalConfiguration.current.screenWidthDp.div(3).dp), it)
    }
}

@Composable
private fun Pie(modifier: Modifier = Modifier, values: List<ChartElement>) {
    Canvas(modifier = modifier) {
        var startAngle = 0f
        values.forEach { entry ->
            val sweepAngle = entry.value * 360f
            drawArc(
                color = entry.color,
                startAngle = startAngle, sweepAngle = sweepAngle, useCenter = true,
                size = size
            )
            startAngle += sweepAngle
        }
    }
}