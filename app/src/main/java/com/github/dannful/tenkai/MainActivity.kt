package com.github.dannful.tenkai

import android.app.DownloadManager
import android.content.Context
import android.os.Bundle
import android.os.Environment
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
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var dataStore: DataStore<Preferences>

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
                    scope.launch {
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
            val downloadManager = context.getSystemService(DownloadManager::class.java)
            val request = DownloadManager.Request(response.url.toUri())
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                .setTitle(context.getString(R.string.download_new_app_version))
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "tenkai_new.apk")
            downloadManager.enqueue(request)
        }
        httpClient.close()
    }

    @Serializable
    private data class AppVersionResponse(
        val tag_name: String,
        val url: String
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
            if(major == other.major && minor == other.minor && patch > other.patch)
                return 1
            if(major == other.major && minor == other.minor && patch == other.patch)
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