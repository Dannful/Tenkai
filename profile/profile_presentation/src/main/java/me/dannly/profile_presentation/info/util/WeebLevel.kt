package me.dannly.profile_presentation.info.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.dannly.core.R

enum class WeebLevel(val requiredExperience: IntRange) {

    NOOB(0 until 50.inMinutes), KONNICHIWA(50.inMinutes until 100.inMinutes),
    KIMETSU(100.inMinutes until 150.inMinutes), HIKIKOMORI(150.inMinutes until 200.inMinutes),
    NEET(200.inMinutes until 250.inMinutes), DISCORD_MODERATOR(250.inMinutes..Int.MAX_VALUE);

    @Composable
    fun getText() = when (this) {
        NOOB -> stringResource(id = R.string.noob_level)
        KONNICHIWA -> stringResource(id = R.string.konnichiwa_level)
        KIMETSU -> stringResource(id = R.string.kimetsu_level)
        HIKIKOMORI -> stringResource(id = R.string.hikikomori_level)
        NEET -> stringResource(id = R.string.neet_level)
        DISCORD_MODERATOR -> stringResource(id = R.string.discord_moderator)
    }
}

private val Int.inMinutes get() = this * 18 * 20