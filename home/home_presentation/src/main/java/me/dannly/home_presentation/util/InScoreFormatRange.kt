package me.dannly.home_presentation.util

import me.dannly.home_domain.model.CachedScoreFormat

fun CachedScoreFormat.containsScore(score: Double) = when (this) {
    CachedScoreFormat.POINT_100 -> score in 0.toDouble()..100.toDouble()
    CachedScoreFormat.POINT_10_DECIMAL -> score in 0.toDouble()..10.toDouble()
    CachedScoreFormat.POINT_10 -> score in 0.toDouble()..10.toDouble()
    CachedScoreFormat.POINT_5 -> score in 0.toDouble()..5.toDouble()
    CachedScoreFormat.POINT_3 -> score in 0.toDouble()..3.toDouble()
    else -> false
}