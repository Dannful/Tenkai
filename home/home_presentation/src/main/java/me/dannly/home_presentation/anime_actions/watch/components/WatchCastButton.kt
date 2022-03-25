package me.dannly.home_presentation.anime_actions.watch.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.dannly.core.R
import me.dannly.core_ui.navigation.Destination
import me.dannly.home_presentation.anime_actions.AnimeDetailsViewModel
import me.dannly.home_presentation.anime_actions.watch.util.launchCaster

@Composable
fun WatchCastButton(viewModel: AnimeDetailsViewModel = Destination.AnimeActions.viewModel()) {
    val context = LocalContext.current
    Button(
        onClick = {
            viewModel.state.currentUrl?.let { launchCaster(context = context, videoURL = it) }
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Text(text = stringResource(id = R.string.launch_caster))
    }
}