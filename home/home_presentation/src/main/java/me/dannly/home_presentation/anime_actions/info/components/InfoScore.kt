package me.dannly.home_presentation.anime_actions.info.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import me.dannly.core.R
import me.dannly.home_domain.model.CachedAnime
import me.dannly.home_domain.model.CachedUserAnime

@Composable
fun InfoScore(cachedAnime: CachedAnime?) {
    cachedAnime?.averageScore?.let { score ->
        OutlinedTextField(value = score.toString(), onValueChange = {}, readOnly = true, label = {
            Text(text = stringResource(id = R.string.score))
        }, modifier = Modifier.fillMaxWidth())
    }
}