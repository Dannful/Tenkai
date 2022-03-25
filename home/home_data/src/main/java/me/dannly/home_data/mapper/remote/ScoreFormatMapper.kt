package me.dannly.home_data.mapper.remote

import me.dannly.graphql.type.ScoreFormat
import me.dannly.home_domain.model.CachedScoreFormat

fun ScoreFormat.toCachedScoreFormat() =
    CachedScoreFormat.values()[ordinal]