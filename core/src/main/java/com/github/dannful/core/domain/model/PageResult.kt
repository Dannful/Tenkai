package com.github.dannful.core.domain.model

data class PageResult<T>(
    val total: Int,
    val items: List<T>
)
