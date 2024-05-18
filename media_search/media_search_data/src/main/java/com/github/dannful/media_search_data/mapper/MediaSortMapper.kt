package com.github.dannful.media_search_data.mapper

import com.github.dannful.media_search_domain.model.MediaSort

fun MediaSort.toDataMediaSort() = com.github.dannful.models.type.MediaSort.valueOf(name)