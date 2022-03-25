package me.dannly.tenkai.components.toolbar

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import me.dannly.core.R

val defaultToolbarTitle
    get() = @Composable {
        Text(text = stringResource(id = R.string.app_name))
    }

fun toolbarTitle(text: String) = @Composable {
    if (text.isNotEmpty()) {
        Text(text = text, overflow = TextOverflow.Ellipsis, maxLines = 1)
    } else {
        val dots by produceState(initialValue = ".") {
            flow {
                while (true) {
                    delay(500)
                    emit("..")
                    delay(500)
                    emit("...")
                    delay(500)
                    emit(".")
                }
            }.onEach { value = it }
                .flowOn(Dispatchers.Default)
                .launchIn(this)
        }
        Text(text = dots, overflow = TextOverflow.Ellipsis, maxLines = 1)
    }
}