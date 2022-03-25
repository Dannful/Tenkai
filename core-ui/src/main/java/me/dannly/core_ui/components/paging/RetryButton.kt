package me.dannly.core_ui.components.paging

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.ui.res.stringResource
import me.dannly.core_ui.R

fun LazyListScope.retryButton(
    enabled: Boolean,
    retry: () -> Unit
) {
    if (enabled) {
        item {
            Text(
                text = stringResource(id = R.string.load_failed),
                style = MaterialTheme.typography.body1
            )
            TextButton(onClick = retry) {
                Text(
                    text = stringResource(id = R.string.retry),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}