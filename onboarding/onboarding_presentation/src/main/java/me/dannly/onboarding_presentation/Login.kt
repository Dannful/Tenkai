package me.dannly.onboarding_presentation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import me.dannly.core.util.UiEvent

private const val CLIENT_LOGIN_URL =
    "https://anilist.co/api/v2/oauth/authorize?client_id=${me.dannly.core.BuildConfig.CLIENT_ID}&redirect_uri=${me.dannly.core.BuildConfig.CLIENT_REDIRECT_URI}&response_type=code"

@OptIn(
    ExperimentalCoroutinesApi::class
)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LoginScreen(onCloseApp: () -> Unit) {
    val viewModel: LoginViewModel = hiltViewModel()
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect {
            when (it) {
                UiEvent.NavigateBack -> Unit
                is UiEvent.ShowToast -> Toast.makeText(
                    context,
                    it.uiText.asString(context),
                    Toast.LENGTH_LONG
                ).show()
                UiEvent.CloseApp -> onCloseApp()
            }
        }
    }
    AndroidView(
        factory = { webViewContext -> WebView(webViewContext) },
        modifier = Modifier.fillMaxSize()
    ) { webView ->
        webView.loadUrl(CLIENT_LOGIN_URL)
        webView.clearCache(true)
        webView.clearHistory()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {

            private val code by lazy {
                webView.url?.replace("${me.dannly.core.BuildConfig.CLIENT_REDIRECT_URI}?code=", "")
            }

            private fun inRedirectURI() {
                code?.let { code ->
                    webView.loadUrl("https://tr-8r.com")
                    viewModel.registerCode(code)
                }
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                if (url?.startsWith(me.dannly.core.BuildConfig.CLIENT_REDIRECT_URI, true) == true)
                    inRedirectURI()
            }
        }
    }
}