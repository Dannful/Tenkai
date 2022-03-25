package me.dannly.tenkaiapp.components.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.dannly.core.R
import me.dannly.core_ui.components.StrokedText

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.25f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.anime_header),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxHeight()
                .fillMaxWidth(0.5f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.tenkai_round),
                contentDescription = null, modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .border(width = 1.dp, color = Color.Black, shape = CircleShape)
            )
            Spacer(modifier = Modifier.height(4.dp))
            StrokedText(
                fontSize = MaterialTheme.typography.h4.fontSize,
                text = stringResource(id = R.string.app_name)
            )
        }
    }
}