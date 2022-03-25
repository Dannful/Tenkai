package me.dannly.core_ui.components

import android.graphics.Rect
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import me.dannly.core_ui.util.toDp
import me.dannly.core_ui.util.toPx

@Composable
fun StrokedText(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.White,
    strokeColor: Color = Color.Black,
    fontSize: TextUnit = 12.sp,
    typeface: Typeface = Typeface.DEFAULT
) {
    val textPaintStroke = Paint().asFrameworkPaint().apply {
        this.typeface = typeface
        isAntiAlias = true
        style = android.graphics.Paint.Style.STROKE
        textSize = fontSize.toPx()
        color = strokeColor.toArgb()
        strokeWidth = 12f
        strokeMiter = 10f
        textAlign = android.graphics.Paint.Align.CENTER
        strokeJoin = android.graphics.Paint.Join.ROUND
    }
    val rect = Rect()
    textPaintStroke.getTextBounds(text, 0, text.length, rect)
    Canvas(
        modifier = modifier
            .size(
                width = rect
                    .width()
                    .toDp(),
                height = rect
                    .height()
                    .toDp()
            )
    ) {
        drawIntoCanvas {
            val textPaint = Paint().asFrameworkPaint().apply {
                this.typeface = typeface
                isAntiAlias = true
                style = android.graphics.Paint.Style.FILL
                textSize = fontSize.toPx()
                textAlign = android.graphics.Paint.Align.CENTER
                color = textColor.toArgb()
            }

            val x = size.width / 2
            val y = (size.height / 2) - ((textPaint.descent() + textPaint.ascent()) / 2)
            it.nativeCanvas.drawText(
                text,
                x,
                y,
                textPaintStroke
            )
            it.nativeCanvas.drawText(
                text,
                x,
                y,
                textPaint
            )
        }
    }
}