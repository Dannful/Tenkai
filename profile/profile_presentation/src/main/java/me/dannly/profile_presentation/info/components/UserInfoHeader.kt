package me.dannly.profile_presentation.info.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.coil.CoilImage
import me.dannly.core.R
import me.dannly.core_ui.components.StrokedText
import me.dannly.core_ui.navigation.Destination
import me.dannly.profile_presentation.ProfileViewModel
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun UserInfoHeader(
    profileViewModel: ProfileViewModel = Destination.Main.Profile.viewModel(),
    bannerHeight: Float
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(bannerHeight),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        profileViewModel.state.user?.imageUrl?.let {
            CoilImage(
                imageModel = it, modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth()
                    .aspectRatio(1f, true)
                    .border(
                        width = 2.dp,
                        color = Color.Black,
                        shape = CircleShape
                    )
                    .clip(CircleShape), contentScale = ContentScale.Crop
            )
        }
        StrokedText(
            modifier = Modifier.fillMaxHeight(0.5f),
            text = profileViewModel.state.user?.name.orEmpty(),
            fontSize = MaterialTheme.typography.h6.fontSize
        )
        profileViewModel.state.user?.createdAt?.let { createdAt ->
            StrokedText(
                text = stringResource(
                    R.string.created_at, Date(createdAt * 1000L).toInstant().atZone(
                        ZoneId.systemDefault()
                    ).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                ),
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}