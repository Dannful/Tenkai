package com.github.dannful.core.data.mapper

import com.apollographql.apollo3.api.Optional
import com.github.dannful.core.domain.model.MediaDate
import com.github.dannful.models.fragment.FuzzyDateFragment
import com.github.dannful.models.type.FuzzyDateInput

fun MediaDate.toFuzzyDateInput() = FuzzyDateInput(
    day = Optional.present(day),
    month = Optional.present(month),
    year = Optional.present(year)
)

fun FuzzyDateFragment.toMediaDate() = day?.let { day ->
    month?.let { month ->
        year?.let { year ->
            MediaDate(day = day, month = month, year = year)
        }
    }
}