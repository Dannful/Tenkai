package com.github.dannful.core.data.mapper

import com.apollographql.apollo3.api.Optional
import com.github.dannful.core.data.model.QueryInput

fun <T> QueryInput<T>.toQuery(): Optional<T?> {
    if(!present)
        return Optional.absent()
    return Optional.present(value)
}