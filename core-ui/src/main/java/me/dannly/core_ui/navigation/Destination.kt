package me.dannly.core_ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.dannly.core.R
import me.dannly.core.util.UiText
import me.dannly.core_ui.theme.LocalNavigation

sealed class Destination(
    val route: Route,
    val routeItem: RouteItem? = null
) {

    @Composable
    inline fun <reified T : ViewModel> viewModel(): T {
        val controller = LocalNavigation.current
        return if (controller.backQueue.any { it.destination.route == route.toString() }) {
            hiltViewModel(LocalNavigation.current.getBackStackEntry(route.toString()))
        } else {
            hiltViewModel()
        }
    }

    val isCurrentDestination: Boolean
        @Composable
        get() {
            val backStackEntryFlow = LocalNavigation.current.currentBackStackEntryFlow
            return produceState(initialValue = false) {
                backStackEntryFlow.onEach {
                    if (it.destination.route?.startsWith(route.toString()) == true) {
                        value = true
                        return@onEach
                    }
                    var parent = it.destination.parent
                    while (parent != null) {
                        if (parent.route?.startsWith(route.toString()) == true) {
                            value = true
                            return@onEach
                        }
                        parent = parent.parent
                    }
                    value = false
                }.flowOn(Dispatchers.Default).launchIn(this)
            }.value
        }


    val children
        get() = this::class.nestedClasses.mapNotNull { it.objectInstance as? Destination }
            .sortedBy { it.routeItem?.position }

    object Login : Destination(
        route = Route("login")
    )

    object Main : Destination(
        route = Route("main")
    ) {

        object Home : Destination(
            route = Route("home"),
            routeItem = RouteItem(
                imageVector = Icons.Default.Home,
                title = UiText.StringResource(R.string.home),
                position = 1
            )
        )

        object Browse : Destination(
            route = Route("browse"),
            routeItem = RouteItem(
                imageVector = Icons.Default.List,
                title = UiText.StringResource(R.string.browse),
                position = 2
            )
        ) {

            object Catalog : Destination(
                route = Route("catalog")
            )

            object Search : Destination(
                route = Route("search")
            ) {

                object Shows : Destination(
                    route = Route("shows"),
                    routeItem = RouteItem(
                        imageVector = Icons.Default.PlayArrow,
                        title = UiText.StringResource(R.string.shows),
                        position = 1
                    )
                )

                object Users : Destination(
                    route = Route("users"),
                    routeItem = RouteItem(
                        imageVector = Icons.Default.Person,
                        title = UiText.StringResource(R.string.users),
                        position = 2
                    )
                )
            }
        }

        object Profile : Destination(
            route = Route(
                name = "profile"
            ),
            routeItem = RouteItem(
                imageVector = Icons.Default.Person,
                title = UiText.StringResource(R.string.profile),
                position = 3
            )
        ) {

            object Info : Destination(
                route = Route(
                    "info", arguments = listOf(
                        RouteArgument(name = "userId", optional = true)
                    )
                ),
                routeItem = RouteItem(
                    imageVector = Icons.Default.Info,
                    title = UiText.StringResource(R.string.info),
                    position = 1
                )
            )

            object Activity : Destination(
                route = Route("activity"),
                routeItem = RouteItem(
                    imageVector = Icons.Default.Warning,
                    title = UiText.StringResource(R.string.activity),
                    position = 2
                )
            )

            object Favourites : Destination(
                route = Route("favourites"),
                routeItem = RouteItem(
                    imageVector = Icons.Default.Favorite,
                    title = UiText.StringResource(R.string.favourites),
                    position = 3
                )
            )

            object Scores : Destination(
                route = Route("scores"),
                routeItem = RouteItem(
                    imageVector = Icons.Default.Star,
                    title = UiText.StringResource(R.string.scores),
                    position = 4
                )
            )
        }

        object Notifications : Destination(
            route = Route("notifications"),
            routeItem = RouteItem(
                imageVector = Icons.Default.Notifications,
                title = UiText.StringResource(R.string.notifications),
                position = 4
            )
        )

        object Settings : Destination(
            route = Route("settings"),
            routeItem = RouteItem(
                imageVector = Icons.Default.Settings,
                title = UiText.StringResource(R.string.settings),
                position = 5
            )
        ) {

            object General : Destination(
                route = Route("general"),
                routeItem = RouteItem(
                    drawable = R.drawable.ic_baseline_tune_24,
                    title = UiText.StringResource(R.string.general),
                    position = 1
                )
            )

            object Sources : Destination(
                route = Route("sources"),
                routeItem = RouteItem(
                    drawable = R.drawable.ic_baseline_language_24,
                    title = UiText.StringResource(R.string.sources),
                    position = 2
                )
            )
        }
    }

    object AnimeActions : Destination(
        route = Route(
            "anime_actions"
        )
    ) {

        object Info : Destination(
            route = Route(
                "anime_info", listOf(
                    RouteArgument("animeId", optional = true)
                )
            ),
            routeItem = RouteItem(
                imageVector = Icons.Default.Info,
                title = UiText.StringResource(R.string.details),
                position = 1
            )
        )

        object Edit : Destination(
            route = Route(
                "anime_edit", listOf(
                    RouteArgument("animeId", optional = true)
                )
            ),
            routeItem = RouteItem(
                imageVector = Icons.Default.Edit,
                title = UiText.StringResource(R.string.edit),
                position = 2
            )
        )

        object Watch : Destination(
            route = Route(
                "anime_watch", listOf(
                    RouteArgument("animeId", optional = true)
                )
            ),
            routeItem = RouteItem(
                imageVector = Icons.Default.PlayArrow,
                title = UiText.StringResource(R.string.watch),
                position = 3
            )
        )
    }
}
