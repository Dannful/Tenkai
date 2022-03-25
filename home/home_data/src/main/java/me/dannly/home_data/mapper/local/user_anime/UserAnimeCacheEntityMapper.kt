package me.dannly.home_data.mapper.local.user_anime

import me.dannly.core.domain.remote.model.AnimeStatus
import me.dannly.core.domain.remote.model.UserAnimeStatus
import me.dannly.home_data.local.entity.UserAnimeCacheEntity
import me.dannly.home_domain.model.CachedAnime
import me.dannly.home_domain.model.CachedUserAnime

fun UserAnimeCacheEntity.toCachedUserAnime() = CachedUserAnime(
    id = id,
    progress = progress,
    status = UserAnimeStatus.valueOf(status),
    score = score,
    updatedAt = updatedAt,
    cachedAnime = CachedAnime(
        id = animeId,
        title = animeTitle,
        episodes = animeEpisodes,
        nextEpisode = animeNextEpisode,
        genres = genres,
        synonyms = synonyms,
        status = AnimeStatus.valueOf(animeStatus),
        averageScore = averageScore,
        synopsis = synopsis,
        bannerUrl = bannerUrl,
        timeUntilNextEpisode = animeAiringTime,
        coverUrl = animeImage

    )
)