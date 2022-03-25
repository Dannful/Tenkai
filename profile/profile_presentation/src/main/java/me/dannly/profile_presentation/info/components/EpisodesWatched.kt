package me.dannly.profile_presentation.info.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.dannly.core_ui.navigation.Destination
import me.dannly.profile_presentation.ProfileViewModel
import me.dannly.core.R

@Composable
fun EpisodesWatched(
    profileViewModel: ProfileViewModel = Destination.Main.Profile.viewModel()
) {
    profileViewModel.state.user?.episodesWatched?.let {
        OutlinedTextField(
            value = it.toString(), onValueChange = {},
            label = {
                Text(text = stringResource(id = R.string.episodes_watched))
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            readOnly = true
        )
    }
}