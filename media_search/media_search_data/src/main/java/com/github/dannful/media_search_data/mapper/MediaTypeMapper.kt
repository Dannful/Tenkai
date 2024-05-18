package com.github.dannful.media_search_data.mapper

import com.github.dannful.media_search_domain.model.MediaType

fun MediaType.toDataMediaType() = com.github.dannful.models.type.MediaType.valueOf(name)