package me.dannly.home_data.mapper.local.user_anime

import me.dannly.home_data.local.entity.UserAnimeCacheEntity
import me.dannly.home_domain.model.CachedUserAnime

fun CachedUserAnime.toUserAnimeCacheEntity() = UserAnimeCacheEntity(
    id,
    cachedAnime.id,
    progress,
    status.name,
    score,
    cachedAnime.averageScore,
    updatedAt,
    cachedAnime.title,
    cachedAnime.episodes,
    cachedAnime.timeUntilNextEpisode,
    cachedAnime.coverUrl,
    cachedAnime.status.name,
    cachedAnime.nextEpisode,
    cachedAnime.genres,
    cachedAnime.synonyms,
    cachedAnime.synopsis,
    cachedAnime.bannerUrl
)