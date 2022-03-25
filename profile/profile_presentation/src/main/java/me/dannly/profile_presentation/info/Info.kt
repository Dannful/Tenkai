package me.dannly.profile_presentation.info

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import me.dannly.core_ui.navigation.Destination
import me.dannly.core_ui.theme.LocalSpacing
import me.dannly.core_ui.util.withOrientation
import me.dannly.profile_presentation.ProfileViewModel
import me.dannly.profile_presentation.components.UserLoadFailed
import me.dannly.profile_presentation.info.components.*

@Composable
fun UserInfoScreen(
    profileViewModel: ProfileViewModel = Destination.Main.Profile.viewModel()
) {
    UserLoadFailed()
    val bannerHeight = withOrientation(portrait = 0.3f, landscape = 0.5f)
    val spacing = LocalSpacing.current
    AnimatedVisibility(visible = profileViewModel.state.user == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
    UserBanner(banner = profileViewModel.state.user?.bannerUrl, bannerHeight = bannerHeight)
    Column(
        verticalArrangement = Arrangement.spacedBy(spacing.spaceExtraSmall),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        UserInfoBody(bannerHeight = bannerHeight)
    }
}