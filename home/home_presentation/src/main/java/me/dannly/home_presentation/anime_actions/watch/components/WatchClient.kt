package me.dannly.home_presentation.anime_actions.watch.components

import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import me.dannly.home_presentation.anime_actions.AnimeDetailsEvent
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel

class WatchClient(
    private val animeDetailsViewModel: AnimeDetailsViewModel,
    private val animeId: Int
) : WebViewClient() {

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        if (url?.trimEnd('/') != animeDetailsViewModel.state.currentUrl?.trimEnd('/'))
            animeDetailsViewModel.onWatchEvent(AnimeDetailsEvent.AnimeWatchEvent.SetUrl(url.orEmpty()))
        animeDetailsViewModel.onWatchEvent(
            AnimeDetailsEvent.AnimeWatchEvent.SetAdd(
                view?.url?.trimEnd(
                    '/'
                ) != animeDetailsViewModel.currentlySelectedSource?.sources?.find { it.animeId == animeId }?.url?.trimEnd(
                    '/'
                )
            )
        )
    }
}