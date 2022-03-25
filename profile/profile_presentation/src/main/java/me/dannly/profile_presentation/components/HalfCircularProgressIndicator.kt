package me.dannly.profile_presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun HalfCircularProgressIndicator(
    modifier: Modifier = Modifier,
    indicatorValue: Float = 0f,
    maxIndicatorValue: Float = 1f,
    backgroundIndicatorColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
    backgroundIndicatorStrokeWidth: Float = 100f,
    foregroundIndicatorColor: Color = MaterialTheme.colors.primary,
    foregroundIndicatorStrokeWidth: Float = 100f
) {
    var animatedIndicatorValue by remember {
        mutableStateOf(0f)
    }
    LaunchedEffect(key1 = indicatorValue) {
        animatedIndicatorValue = indicatorValue
    }
    val sweepAngle by animateFloatAsState(
        targetValue = 240f * (animatedIndicatorValue.coerceAtMost(maxIndicatorValue) / maxIndicatorValue),
        animationSpec = tween(1000)
    )
    Canvas(modifier = modifier) {
        val indicatorSize = size * 0.8f
        indicator(
            componentSize = indicatorSize,
            indicatorColor = backgroundIndicatorColor,
            indicatorStrokeWidth = backgroundIndicatorStrokeWidth
        )
        indicator(
            sweepAngle = sweepAngle,
            componentSize = indicatorSize,
            indicatorColor = foregroundIndicatorColor,
            indicatorStrokeWidth = foregroundIndicatorStrokeWidth
        )
    }
}

private fun DrawScope.indicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
    sweepAngle: Float = 240f
) {
    drawArc(
        size = componentSize,
        color = indicatorColor,
        useCenter = false,
        startAngle = 150f,
        sweepAngle = sweepAngle,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ), topLeft = Offset(
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}