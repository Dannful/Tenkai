package com.github.dannful.core.domain.model.coroutines

enum class UserScoreFormat {

    BASE_100, BASE_10_DECIMAL, BASE_10, BASE_5, BASE_3;

    val valueRange: ClosedFloatingPointRange<Float>
        get() = when (this) {
            BASE_100 -> 0f..100f
            BASE_10_DECIMAL -> 0f..10f
            BASE_10 -> 0f..10f
            BASE_5 -> 0f..5f
            BASE_3 -> 0f..3f
        }

    val disallowedRegexFilter: Regex
        get() = when (this) {
            BASE_100, BASE_5, BASE_3, BASE_10 -> "\\D"
            BASE_10_DECIMAL -> "[^\\d.]"
        }.toRegex()

    val allowedRegexFilter: Regex
        get() = when (this) {
            BASE_100, BASE_5, BASE_3, BASE_10 -> "\\d+"
            BASE_10_DECIMAL -> "^\\d+$|^\\d+.\\d+$"
        }.toRegex()
}