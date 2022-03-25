package me.dannly.core.data.mapper

import me.dannly.core.domain.remote.model.UserAnimeSort
import me.dannly.graphql.type.MediaListSort

fun MediaListSort.toUserAnimeSort() = UserAnimeSort
    .valueOf(name)