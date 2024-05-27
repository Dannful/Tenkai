package com.github.dannful.core.data.model

import kotlinx.serialization.Serializable

@Serializable
class QueryInput<T>(
    val value: T?,
    val present: Boolean
) {

    fun <V> map(transform: (T?) -> V): QueryInput<V> = QueryInput(transform(value), present)

    fun getOrDefault(defaultValue: T?) = if(present) value else defaultValue

    companion object {

        fun <T> present(value: T?): QueryInput<T> = QueryInput(value, true)
        fun <T> presentIfNotNull(value: T?): QueryInput<T> = QueryInput(value, value != null)
        fun <T> absent(): QueryInput<T> = QueryInput(null, false)
    }
}