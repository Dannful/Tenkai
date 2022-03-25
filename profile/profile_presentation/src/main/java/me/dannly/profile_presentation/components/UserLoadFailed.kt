package me.dannly.profile_presentation.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import me.dannly.core.util.UiEvent
import me.dannly.core_ui.navigation.Destination
import me.dannly.core_ui.theme.LocalNavigation
import me.dannly.profile_presentation.ProfileViewModel

@Composable
fun UserLoadFailed(profileViewModel: ProfileViewModel = Destination.Main.Profile.viewModel()) {
    val controller = LocalNavigation.current
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        profileViewModel.uiEvent.collect {
            when (it) {
                UiEvent.NavigateBack -> controller.popBackStack()
                is UiEvent.ShowToast -> Toast.makeText(
                    context,
                    it.uiText.asString(context),
                    Toast.LENGTH_LONG
                ).show()
                else -> Unit
            }
        }
    }
}