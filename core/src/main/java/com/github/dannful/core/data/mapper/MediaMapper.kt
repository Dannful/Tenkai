package com.github.dannful.core.data.mapper

import com.github.dannful.core.data.entity.MediaEntity
import com.github.dannful.core.domain.model.Media
import com.github.dannful.core.domain.model.MediaStatus
import com.github.dannful.core.domain.model.MediaTitle
import com.github.dannful.models.fragment.MediaFragment

fun MediaFragment.toDomainMedia() = Media(
    id = id,
    description = description,
    genres = genres?.filterNotNull() ?: emptyList(),
    bannerUrl = bannerImage,
    episodes = episodes,
    timeUntilNextEpisode = nextAiringEpisode?.timeUntilAiring,
    status = status?.let { MediaStatus.valueOf(it.name) } ?: MediaStatus.UNKNOWN,
    nextEpisode = nextAiringEpisode?.episode,
    coverImageUrl = coverImage?.medium,
    titles = MediaTitle(
        romaji = title!!.romaji!!,
        english = title?.english,
        native = title!!.native!!,
    ),
    synonyms = synonyms?.asSequence()?.minus(title?.english)?.minus(title?.romaji)
        ?.minus(title?.native)
        ?.filterNotNull()?.distinct()?.toList() ?: emptyList()
)

fun Media.toEntity() = MediaEntity(
    mediaId = id,
    mediaStatus = status,
    description = description,
    bannerUrl = bannerUrl,
    coverImageUrl = coverImageUrl,
    titles = titles,
    timeUntilNextEpisode = timeUntilNextEpisode,
    episodes = episodes,
    genres = genres,
    nextEpisode = nextEpisode,
    synonyms = synonyms
)