package me.dannly.tenkai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.work.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.dannly.core.domain.preferences.Preferences
import me.dannly.core_ui.navigation.Destination
import me.dannly.core_ui.theme.LocalNavigation
import me.dannly.core_ui.theme.LocalScaffoldState
import me.dannly.home_presentation.user_list.UserAnimesScreen
import me.dannly.notifications_domain.repository.NotificationsRepository
import me.dannly.notifications_presentation.NotificationsScreen
import me.dannly.onboarding_presentation.LoginScreen
import me.dannly.tenkai.components.AppScaffold
import me.dannly.tenkai.components.navigation.graphs.animeActionsNavGraph
import me.dannly.tenkai.components.navigation.scaffold_items.getNavActions
import me.dannly.tenkai.components.navigation.scaffold_items.navBottomBar
import me.dannly.tenkai.components.navigation.scaffold_items.navRail
import me.dannly.tenkai.components.navigation.scaffold_items.navTitle
import me.dannly.tenkai.worker.MediaNotificationsWorker
import me.dannly.tenkaiapp.components.navigation.graphs.browseNavGraph
import me.dannly.tenkaiapp.components.navigation.graphs.profileNavGraph
import me.dannly.tenkaiapp.components.navigation.graphs.settingsNavGraph
import me.dannly.tenkaiapp.ui.theme.TenkaiAppTheme
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    @Inject
    lateinit var notificationsRepository: NotificationsRepository

    private fun enqueueNotificationsWork() {
        val mediaNotificationsWorkRequest = PeriodicWorkRequestBuilder<MediaNotificationsWorker>(
            15, TimeUnit.MINUTES, 5, TimeUnit.MINUTES
        ).setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "media_notifications",
            ExistingPeriodicWorkPolicy.KEEP,
            mediaNotificationsWorkRequest
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enqueueNotificationsWork()
        setContent {
            TenkaiAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val scaffoldState = rememberScaffoldState()
                    val navController = LocalNavigation.current
                    val loggedIn = preferences.getAccessToken()
                        .collectAsState(initial = null, Dispatchers.IO).value != null
                            && preferences.getUserId()
                        .collectAsState(initial = null, Dispatchers.IO).value != null
                    val unreadNotificationCount by produceState(initialValue = 0) {
                        value = notificationsRepository.retrieveUnreadNotificationCount()
                            .getOrDefault(0)
                    }
                    val coroutineScope = rememberCoroutineScope()
                    AppScaffold(
                        scaffoldState = scaffoldState,
                        loggedIn = loggedIn,
                        bottomNavigationItems = navBottomBar,
                        actions = getNavActions(unreadNotificationCount),
                        title = navTitle,
                        navigationRailItems = navRail
                    ) {
                        CompositionLocalProvider(LocalScaffoldState provides scaffoldState) {
                            Loading(loggedIn) {
                                NavHost(
                                    navController = navController,
                                    startDestination = if (loggedIn) Destination.Main.Home.route.toString() else Destination.Login.route.toString()
                                ) {
                                    composable(route = Destination.Login.route.toString()) {
                                        LoginScreen {
                                            finishAffinity()
                                        }
                                    }
                                    composable(route = Destination.Main.Home.route.toString()) {
                                        UserAnimesScreen {
                                            coroutineScope.launch {
                                                val route =
                                                    Destination.AnimeActions.Info.route.withArguments(
                                                        it.toString()
                                                    )
                                                navController.navigate(route)
                                            }
                                        }
                                    }
                                    composable(route = Destination.Main.Notifications.route.toString()) {
                                        NotificationsScreen(
                                            onAiringNotificationClick = {
                                                coroutineScope.launch {
                                                    navController.navigate(
                                                        Destination.AnimeActions.Info.route.withArguments(
                                                            it.toString()
                                                        )
                                                    )
                                                }
                                            },
                                            onRelatedMediaAdditionNotificationClick = {
                                                coroutineScope.launch {
                                                    navController.navigate(
                                                        Destination.AnimeActions.Info.route.withArguments(
                                                            it.toString()
                                                        )
                                                    )
                                                }
                                            },
                                            onActivityLikeNotificationClick = {
                                                coroutineScope.launch {
                                                    navController.navigate(
                                                        Destination.Main.Profile.Info.route.withArguments(
                                                            it.toString()
                                                        )
                                                    )
                                                }
                                            }
                                        )
                                    }
                                    animeActionsNavGraph()
                                    browseNavGraph(navController, coroutineScope)
                                    profileNavGraph(navController, coroutineScope)
                                    settingsNavGraph(preferences)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun Loading(loggedIn: Boolean?, content: @Composable () -> Unit) {
        if (loggedIn == null) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        } else {
            content()
        }
    }
}