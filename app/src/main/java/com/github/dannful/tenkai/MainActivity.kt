package com.github.dannful.tenkai

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.core.util.Constants
import com.github.dannful.core_ui.LocalSpacingProvider
import com.github.dannful.core_ui.navigation.Route
import com.github.dannful.home_presentation.HomeFloatingActionButton
import com.github.dannful.home_presentation.HomeStatusNavigation
import com.github.dannful.home_presentation.homeSection
import com.github.dannful.media_search_presentation.searchRoute
import com.github.dannful.onboarding_presentation.onboardingScreen
import com.github.dannful.tenkai.ui.theme.TenkaiTheme
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStore: DataStore<Preferences>

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TenkaiTheme {
                val scope = rememberCoroutineScope()
                val context = LocalContext.current
                val navigator = rememberNavController()
                val backStackEntry by navigator.currentBackStackEntryAsState()
                val startDestination by dataStore.data.mapLatest {
                    if (it[stringPreferencesKey(Constants.DATA_STORE_TOKEN_KEY_NAME)] == null) Route.Onboarding else Route.Home
                }.collectAsState(initial = null)
                LaunchedEffect(key1 = Unit) {
                    scope.launch(dispatcherProvider.IO) {
                        checkVersion(context = context)
                    }
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        when {
                            backStackEntry?.destination?.hasRoute<Route.Home.ListView>() == true -> HomeFloatingActionButton(
                                navHostController = navigator
                            )

                            else -> {}
                        }
                    }
                ) { innerPadding ->
                    Row(horizontalArrangement = Arrangement.spacedBy(LocalSpacingProvider.current.small)) {
                        when {
                            backStackEntry?.destination?.hasRoute<Route.Home.ListView>() == true -> HomeStatusNavigation(
                                navigator
                            )

                            else -> {}
                        }
                        NavHost(
                            navController = navigator,
                            startDestination = startDestination ?: return@Scaffold,
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            onboardingScreen(navigator)
                            navigation<Route.Home>(startDestination = Route.Home.ListView) {
                                homeSection(navigator)
                                searchRoute()
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun checkVersion(context: Context) {
        try {
            val httpClient = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                    })
                }
            }
            val response =
                httpClient.get(urlString = "https://api.github.com/repos/dannful/Tenkai/releases/latest")
                    .body<AppVersionResponse>()
            if (AppVersion.fromString(response.tag_name) > AppVersion.fromString(BuildConfig.VERSION_NAME)) {
                File(
                    Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS
                    ), context.getString(R.string.new_apk_name)
                ).apply {
                    if (exists())
                        delete()
                }
                val downloadManager = context.getSystemService(DownloadManager::class.java)
                val asset = response.assets.firstOrNull() ?: return
                val request = DownloadManager.Request(asset.browser_download_url.toUri())
                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                    .setTitle(context.getString(R.string.new_apk_name))
                    .setDestinationInExternalPublicDir(
                        Environment.DIRECTORY_DOWNLOADS,
                        getString(R.string.new_apk_name)
                    )
                downloadManager.enqueue(request)
            }
            httpClient.close()
        } catch (_: NoTransformationFoundException) {
            Log.e(
                context.getString(
                    com.github.dannful.core_ui.R.string.app_name
                ), "No release versions were found."
            )
        }
    }

    @Serializable
    private data class AppVersionResponse(
        val tag_name: String,
        val assets: List<Asset>
    )

    @Serializable
    private data class Asset(
        val browser_download_url: String
    )

    private data class AppVersion(
        val patch: Int,
        val minor: Int,
        val major: Int
    ) : Comparable<AppVersion> {

        override fun compareTo(other: AppVersion): Int {
            if (major > other.major)
                return 1
            if (major == other.major && minor > other.minor)
                return 1
            if (major == other.major && minor == other.minor && patch > other.patch)
                return 1
            if (major == other.major && minor == other.minor && patch == other.patch)
                return 0
            return -1
        }

        companion object {

            fun fromString(version: String): AppVersion {
                val versionFields = version.split(".")
                return AppVersion(
                    patch = versionFields[2].toInt(),
                    minor = versionFields[1].toInt(),
                    major = versionFields[0].toInt()
                )
            }
        }
    }
}