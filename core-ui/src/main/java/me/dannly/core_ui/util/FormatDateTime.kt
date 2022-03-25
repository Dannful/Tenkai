package me.dannly.core_ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import me.dannly.core.R

@Composable
fun formatDateTime(localDateTime: LocalDateTime): String {
    val now = LocalDateTime.now()
    return if (localDateTime.year == now.year) {
        if (localDateTime.monthValue == now.monthValue) {
            when (localDateTime.dayOfMonth) {
                now.dayOfMonth -> stringResource(
                    id = R.string.time_ago,
                    now.hour - localDateTime.hour,
                    stringResource(id = R.string.hour)
                )
                else -> stringResource(
                    id = R.string.time_ago,
                    now.dayOfMonth - localDateTime.dayOfMonth,
                    stringResource(id = R.string.day)
                )
            }
        } else {
            stringResource(
                id = R.string.time_ago,
                now.monthValue - localDateTime.monthValue,
                stringResource(id = R.string.month)
            )
        }
    } else {
        localDateTime.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
    }
}