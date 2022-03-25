package me.dannly.profile_presentation.activity

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import me.dannly.core_ui.components.paging.PagedLazyColumn
import me.dannly.core_ui.navigation.Destination
import me.dannly.profile_presentation.ProfileViewModel
import me.dannly.profile_presentation.activity.components.ListActivity
import me.dannly.profile_presentation.activity.components.MessageActivity
import me.dannly.profile_presentation.components.UserLoadFailed

@Composable
fun ActivityScreen(
    profileViewModel: ProfileViewModel = Destination.Main.Profile.viewModel(),
    onListActivityClick: (Int) -> Unit,
    onMessageActivityClick: (Int) -> Unit
) {
    UserLoadFailed()
    val lazyPagingItems = profileViewModel.state.activities.collectAsLazyPagingItems()
    PagedLazyColumn(
        modifier = Modifier.fillMaxSize(),
        lazyPagingItems = lazyPagingItems,
        key = { activity ->
            (activity.asListActivity?.id ?: activity.asMessageActivity?.id)!!
        }
    ) { activity ->
        if (activity != null) {
            ListActivity(
                listActivity = activity.asListActivity,
                lazyPagingItems = lazyPagingItems,
                onListActivityClick = onListActivityClick
            )
            MessageActivity(
                messageActivity = activity.asMessageActivity,
                lazyPagingItems = lazyPagingItems,
                onMessageActivityClick = onMessageActivityClick
            )
        }
    }
}