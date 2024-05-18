package com.github.dannful.tenkai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
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
                val navigator = rememberNavController()
                val backStackEntry by navigator.currentBackStackEntryAsState()
                val startDestination by dataStore.data.mapLatest {
                    if (it[stringPreferencesKey(Constants.DATA_STORE_TOKEN_KEY_NAME)] == null) Route.Onboarding else Route.Home
                }.collectAsState(initial = null)
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
}