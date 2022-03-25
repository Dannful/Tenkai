package me.dannly.profile_presentation.info.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import me.dannly.core.R
import me.dannly.core_ui.components.dialog.InputDialog

@Composable
fun UserInfoBody(
    bannerHeight: Float
) {
    var dialogVisible by remember {
        mutableStateOf(false)
    }
    UserInfoHeader(bannerHeight = bannerHeight)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        val weebLevels = stringResource(id = R.string.weeb_levels)
        val inMinutes = stringResource(id = R.string.in_minutes)
        MeanScore()
        InputDialog(title = buildAnnotatedString {
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                append(weebLevels)
            }
            withStyle(SpanStyle(fontSize = MaterialTheme.typography.subtitle2.fontSize)) {
                append(" ($inMinutes)")
            }
        }, content = {
            LevelsText()
        }, visible = dialogVisible, dismiss = { dialogVisible = false })
        EpisodesWatched()
        CurrentWeebLevel {
            dialogVisible = true
        }
    }
}