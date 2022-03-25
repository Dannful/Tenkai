package me.dannly.profile_presentation.activity.components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeableState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.paging.compose.LazyPagingItems
import me.dannly.core_ui.components.dialog.DeleteConfirmationDialog
import me.dannly.core_ui.navigation.Destination
import me.dannly.profile_domain.model.activity.RetrievedActivity
import me.dannly.profile_presentation.ProfileViewModel
import me.dannly.profile_presentation.activity.util.SwipeDirection

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActivityDelete(
    swipeableState: SwipeableState<SwipeDirection>,
    activityId: Int,
    lazyPagingItems: LazyPagingItems<RetrievedActivity>,
    profileViewModel: ProfileViewModel = Destination.Main.Profile.viewModel()
) {
    var visible by rememberSaveable {
        mutableStateOf(false)
    }
    DeleteConfirmationDialog(deleteDialogVisible = visible, dismiss = { visible = false }) {
        profileViewModel.deleteActivity(activityId) {
            lazyPagingItems.refresh()
        }
    }
    LaunchedEffect(swipeableState.currentValue) {
        when (swipeableState.currentValue) {
            SwipeDirection.CENTER -> {}
            SwipeDirection.RIGHT -> visible = true
        }
        swipeableState.animateTo(SwipeDirection.CENTER)
    }
}