package me.dannly.home_presentation.anime_actions.info.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import me.dannly.core.R
import me.dannly.home_domain.model.CachedAnime
import me.dannly.home_domain.model.CachedUserAnime

@Composable
fun InfoGenres(cachedAnime: CachedAnime?) {
    cachedAnime?.genres?.let { genres ->
        OutlinedTextField(
            textStyle = TextStyle(textAlign = TextAlign.Justify),
            value = genres.joinToString(),
            onValueChange = {},
            readOnly = true,
            label = {
                Text(text = stringResource(id = R.string.genres))
            }, modifier = Modifier.fillMaxWidth()
        )
    }
}