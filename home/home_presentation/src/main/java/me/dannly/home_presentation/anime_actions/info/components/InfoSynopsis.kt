package me.dannly.home_presentation.anime_actions.info.components

import android.text.Html
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.dannly.core.R
import me.dannly.home_domain.model.CachedAnime
import me.dannly.home_domain.model.CachedUserAnime

@Composable
fun InfoSynopsis(modifier: Modifier = Modifier, cachedAnime: CachedAnime?) {
    cachedAnime?.synopsis?.let { synopsis ->
        OutlinedTextField(
            value = Html.fromHtml(synopsis).toString(),
            label = {
                Text(text = stringResource(id = R.string.synopsis))
            },
            onValueChange = {},
            readOnly = true,
            modifier = modifier
                .heightIn(max = 200.dp)
                .fillMaxWidth()
                .verticalScroll(
                    rememberScrollState()
                ),
            textStyle = TextStyle(textAlign = TextAlign.Justify)
        )
    }
}