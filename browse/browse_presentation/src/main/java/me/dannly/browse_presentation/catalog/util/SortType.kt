package me.dannly.browse_presentation.catalog.util

enum class SortType {

    ASCENDING, DESCENDING;

    fun <T> returnIf(ascending: T, descending: T) = when (this) {
        ASCENDING -> ascending
        DESCENDING -> descending
    }
}