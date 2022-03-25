package me.dannly.profile_presentation.info.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun UserBanner(
    banner: String?,
    bannerHeight: Float
) {
    if (banner != null) {
        CoilImage(
            imageModel = banner, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(bannerHeight), contentScale = ContentScale.Crop
        )
    }
}