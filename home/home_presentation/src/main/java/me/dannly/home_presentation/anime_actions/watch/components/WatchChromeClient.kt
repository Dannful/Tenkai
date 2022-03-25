package me.dannly.home_presentation.anime_actions.watch.components

import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView

class WatchChromeClient : WebChromeClient() {

    override fun onJsAlert(
        view: WebView,
        url: String,
        message: String,
        result: JsResult
    ): Boolean {
        result.cancel()
        return true
    }

    override fun onJsConfirm(
        view: WebView,
        url: String,
        message: String,
        result: JsResult
    ): Boolean {
        result.cancel()
        return true
    }

    override fun onJsPrompt(
        view: WebView,
        url: String,
        message: String,
        defaultValue: String,
        result: JsPromptResult
    ): Boolean {
        result.cancel()
        return true
    }
}