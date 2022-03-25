package me.dannly.home_presentation.user_list.util

import java.util.concurrent.TimeUnit

fun formatEpisodeTime(timeUntilAired: Long): String {
    return "%dM %dd %dh %dm %ds".format(
        TimeUnit.SECONDS.toDays(timeUntilAired) / 30,
        TimeUnit.SECONDS.toDays(timeUntilAired) % 30,
        TimeUnit.SECONDS.toHours(timeUntilAired) % TimeUnit.DAYS.toHours(1),
        TimeUnit.SECONDS.toMinutes(timeUntilAired) % TimeUnit.HOURS.toMinutes(1),
        timeUntilAired % TimeUnit.MINUTES.toSeconds(1),
    ).replace("^0.".toRegex(), "")
        .replace("\\D0.".toRegex(), "").trim()
}