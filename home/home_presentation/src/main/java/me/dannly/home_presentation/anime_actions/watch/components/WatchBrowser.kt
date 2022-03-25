package me.dannly.home_presentation.anime_actions.watch.components

import android.annotation.SuppressLint
import android.webkit.WebView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import me.dannly.core_ui.navigation.Destination
import me.dannly.home_presentation.anime_actions.AnimeDetailsEvent
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WatchBrowser(
    modifier: Modifier = Modifier,
    animeId: Int,
    viewModel: AnimeDetailsViewModel = Destination.AnimeActions.viewModel()
) {
    LaunchedEffect(viewModel.state.currentlySelectedSourceIndex) {
        viewModel.currentlySelectedSource?.sources?.find { it.animeId == animeId }?.url?.let {
            viewModel.onWatchEvent(AnimeDetailsEvent.AnimeWatchEvent.SetUrl(it))
            return@LaunchedEffect
        }
        viewModel.currentlySelectedSource?.favourite?.url?.let {
            viewModel.onWatchEvent(AnimeDetailsEvent.AnimeWatchEvent.SetUrl(it))
        }
    }
    AndroidView(factory = { context ->
        WebView(context).apply {
            clearCache(true)
            clearHistory()
            settings.userAgentString =
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36 OPR/81.0.4196.61"
            settings.javaScriptEnabled = true
            settings.mediaPlaybackRequiresUserGesture = false
            settings.domStorageEnabled = true
            settings.loadWithOverviewMode = true
            webChromeClient = WatchChromeClient()
            webViewClient = WatchClient(viewModel, animeId)
            loadUrl("https://www.google.com")
        }
    }, modifier = modifier
        .padding(start = 8.dp, end = 8.dp)
        .fillMaxSize()
        .clip(RoundedCornerShape(4.dp))
        .border(
            width = 2.dp,
            brush = Brush.radialGradient(
                listOf(
                    MaterialTheme.colors.surface,
                    MaterialTheme.colors.onSurface
                )
            ),
            shape = RoundedCornerShape(4.dp)
        ), update = { webView ->
        viewModel.state.currentUrl?.let {
            if (it.trimEnd('/') != webView.url?.trimEnd('/'))
                webView.loadUrl(it)
        }
    })
}