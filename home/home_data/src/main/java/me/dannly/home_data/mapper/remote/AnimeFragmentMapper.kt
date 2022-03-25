package me.dannly.home_data.mapper.remote

import me.dannly.core.domain.remote.model.AnimeStatus
import me.dannly.graphql.fragment.AnimeFragment
import me.dannly.home_domain.model.CachedAnime

fun AnimeFragment.toCachedAnime() = CachedAnime(
    id = id,
    title = title!!.userPreferred!!,
    synonyms = synonyms?.asSequence()
        ?.plus(title?.romaji)
        ?.plus(title?.english)
        ?.plus(title?.native_)?.filterNotNull()?.distinct()
        ?.toList()
        ?: emptyList(),
    genres = genres?.filterNotNull() ?: emptyList(),
    episodes = episodes,
    status = AnimeStatus.valueOf(status!!.name),
    averageScore = averageScore,
    synopsis = description,
    nextEpisode = nextAiringEpisode?.episode,
    timeUntilNextEpisode = nextAiringEpisode?.timeUntilAiring,
    bannerUrl = bannerImage,
    coverUrl = coverImage?.medium
)