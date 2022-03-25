package me.dannly.home_presentation.anime_actions.info.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.request.ImageRequest
import coil.size.Scale
import com.skydoves.landscapist.coil.CoilImage
import me.dannly.core_ui.util.WithOrientation
import me.dannly.home_domain.model.CachedAnime

@Composable
fun InfoBanner(modifier: Modifier = Modifier, cachedAnime: CachedAnime?) {
    cachedAnime?.bannerUrl?.let { bannerUrl ->
        WithOrientation(portrait = {
            CoilImage(
                imageRequest = ImageRequest.Builder(LocalContext.current)
                    .data(bannerUrl)
                    .scale(Scale.FIT)
                    .crossfade(1000)
                    .build(),
                contentScale = ContentScale.Crop,
                modifier = modifier
            )
        }) {
            CoilImage(
                imageRequest = ImageRequest.Builder(LocalContext.current)
                    .data(bannerUrl)
                    .scale(Scale.FIT)
                    .crossfade(1000)
                    .build(),
                contentScale = ContentScale.Crop,
                modifier = modifier,
                alignment = Alignment.TopStart
            )
        }
    }
}