package com.github.dannful.core.data.mapper

import com.github.dannful.core.data.model.QueryInput
import com.github.dannful.core.domain.model.UserMediaUpdate
import com.github.dannful.models.fragment.MediaListFragment

fun MediaListFragment.toUserMediaUpdate() = UserMediaUpdate(
    id = QueryInput.present(id),
    mediaId = QueryInput.present(media!!.mediaFragment.id),
    score = QueryInput.presentIfNotNull(score),
    progress = QueryInput.presentIfNotNull(progress),
    startedAt = QueryInput.presentIfNotNull(startedAt?.fuzzyDateFragment?.toMediaDate()),
    completedAt = QueryInput.presentIfNotNull(completedAt?.fuzzyDateFragment?.toMediaDate()),
    status = QueryInput.presentIfNotNull(status?.toDomainMediaListStatus())
)