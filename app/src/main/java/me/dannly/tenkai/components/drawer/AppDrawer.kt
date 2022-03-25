package me.dannly.tenkaiapp.components.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import me.dannly.core_ui.util.screenHeight
import me.dannly.core_ui.util.screenWidth
import me.dannly.core_ui.util.toPx
import me.dannly.core_ui.util.withOrientation
import me.dannly.tenkai.components.drawer.DrawerItems

private const val widthFraction = 0.75f

private val drawerWidth: Dp
    @Composable
    get() = withOrientation(
        portrait = screenWidth(widthFraction), landscape = screenHeight(
            widthFraction
        )
    )
private val drawerHeight: Dp
    @Composable
    get() = screenHeight()

val drawerShape: Shape
    @Composable
    get() {
        val widthPx = drawerWidth.toPx()
        val heightPx = drawerHeight.toPx()
        return object : Shape {
            override fun createOutline(
                size: Size,
                layoutDirection: LayoutDirection,
                density: Density
            ): Outline {
                return Outline.Rounded(
                    RoundRect(
                        Rect(Offset.Zero, Size(widthPx, heightPx)),
                        CornerRadius(12f, 12f)
                    )
                )
            }
        }
    }

@Composable
fun drawerContent(
    drawerState: DrawerState
): @Composable ColumnScope.() -> Unit = {
    Column(
        modifier = Modifier
            .width(drawerWidth)
            .height(drawerHeight)
    ) {
        DrawerHeader()
        DrawerItems(drawerState)
    }
}