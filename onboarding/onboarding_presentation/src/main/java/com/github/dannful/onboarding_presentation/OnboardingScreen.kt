package com.github.dannful.onboarding_presentation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.github.dannful.core.BuildConfig
import com.github.dannful.core_ui.navigation.Route

private const val LOGIN_URL =
    "https://anilist.co/api/v2/oauth/authorize?client_id=${BuildConfig.CLIENT_ID}&redirect_uri=${
        BuildConfig.CLIENT_REDIRECT_URL
    }&response_type=code"

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun Onboarding(
    onRetrieveCodeAndSubmit: (String) -> Unit
) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webViewClient = object : WebViewClient() {

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    if (url?.startsWith(BuildConfig.CLIENT_REDIRECT_URL) == false) return
                    onRetrieveCodeAndSubmit(url ?: return)
                }
            }
        }
    }, modifier = Modifier.fillMaxSize(), update = {
        it.loadUrl(LOGIN_URL)
    })
}

fun NavGraphBuilder.onboardingScreen(navHostController: NavHostController) {
    navigation<Route.Onboarding>(startDestination = Route.Onboarding.Login) {
        composable<Route.Onboarding.Login> {
            val viewModel = hiltViewModel<OnboardingViewModel>()
            Onboarding(onRetrieveCodeAndSubmit = {
                viewModel.retrieveCodeAndSubmit(it) {
                    navHostController.navigate(Route.Home)
                }
            })
        }
    }
}