package me.dannly.core_ui.components

import androidx.annotation.DrawableRes
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

@Composable
fun DrawableOrVectorIcon(
    modifier: Modifier = Modifier,
    @DrawableRes drawable: Int? = null,
    imageVector: ImageVector? = null,
    contentDescription: String?
) {
    if (drawable != null) {
        Icon(
            painter = painterResource(id = drawable),
            contentDescription = contentDescription,
            modifier = modifier
        )
    } else if (imageVector != null) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
}