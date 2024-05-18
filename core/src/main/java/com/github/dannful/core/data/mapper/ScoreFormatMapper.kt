package com.github.dannful.core.data.mapper

import com.github.dannful.core.domain.model.coroutines.UserScoreFormat
import com.github.dannful.models.type.ScoreFormat

fun ScoreFormat.toUserScoreFormat() = UserScoreFormat.entries[ordinal]