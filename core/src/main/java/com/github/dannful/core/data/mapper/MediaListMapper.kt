package com.github.dannful.core.data.mapper

import com.github.dannful.core.domain.model.UserMedia
import com.github.dannful.core.domain.model.UserMediaStatus
import com.github.dannful.models.fragment.MediaListFragment

fun MediaListFragment.toDomainMediaList() = UserMedia(
    id = id,
    title = media?.mediaFragment?.title?.userPreferred.orEmpty(),
    progress = progress ?: 0,
    status = status?.toDomainMediaListStatus() ?: UserMediaStatus.UNKNOWN,
    media = media!!.mediaFragment.toDomainMedia(),
    score = score!!,
    startedAt = startedAt?.fuzzyDateFragment?.toMediaDate(),
    completedAt = completedAt?.fuzzyDateFragment?.toMediaDate()
)