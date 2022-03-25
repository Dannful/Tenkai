package me.dannly.core.data.mapper

import me.dannly.core.domain.remote.model.AnimeStatus
import me.dannly.graphql.type.MediaStatus

fun MediaStatus.toAnimeStatus() = AnimeStatus.valueOf(
    name
)