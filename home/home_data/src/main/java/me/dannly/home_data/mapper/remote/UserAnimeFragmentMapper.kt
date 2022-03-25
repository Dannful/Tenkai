package me.dannly.home_data.mapper.remote

import me.dannly.core.domain.remote.model.AnimeStatus
import me.dannly.core.domain.remote.model.UserAnimeStatus
import me.dannly.graphql.fragment.UserAnimeFragment
import me.dannly.home_data.local.entity.UserAnimeCacheEntity
import me.dannly.home_domain.model.CachedAnime
import me.dannly.home_domain.model.CachedUserAnime

fun UserAnimeFragment.toUserAnimeCacheEntity(): UserAnimeCacheEntity {
    return UserAnimeCacheEntity(
        id = id,
        progress = progress!!,
        score = score,
        averageScore = media?.fragments?.animeFragment?.averageScore,
        status = status!!.name,
        updatedAt = updatedAt,
        animeTitle = media!!.fragments.animeFragment.title!!.userPreferred!!,
        animeAiringTime = media?.fragments?.animeFragment?.nextAiringEpisode?.timeUntilAiring,
        animeStatus = media!!.fragments.animeFragment.status!!.name,
        animeImage = media?.fragments?.animeFragment?.coverImage?.medium,
        animeEpisodes = media?.fragments?.animeFragment?.episodes,
        animeId = media!!.fragments.animeFragment.id,
        animeNextEpisode = media?.fragments?.animeFragment?.nextAiringEpisode?.episode,
        genres = media?.fragments?.animeFragment?.genres?.filterNotNull() ?: emptyList(),
        synonyms = media?.fragments?.animeFragment?.synonyms?.asSequence()
            ?.plus(media?.fragments?.animeFragment?.title?.english)
            ?.plus(media?.fragments?.animeFragment?.title?.romaji)
            ?.plus(media?.fragments?.animeFragment?.title?.native_)?.filterNotNull()?.distinct()
            ?.toList() ?: emptyList(),
        bannerUrl = media?.fragments?.animeFragment?.bannerImage,
        synopsis = media?.fragments?.animeFragment?.description
    )
}

fun UserAnimeFragment.toCachedUserAnime() = CachedUserAnime(
    id = id,
    progress = progress!!,
    score = score,
    status = UserAnimeStatus.valueOf(status!!.name),
    updatedAt = updatedAt,
    cachedAnime = CachedAnime(
        id = media!!.fragments.animeFragment.id,
        title = media!!.fragments.animeFragment.title!!.userPreferred!!,
        timeUntilNextEpisode = media?.fragments?.animeFragment?.nextAiringEpisode?.timeUntilAiring,
        status = AnimeStatus.valueOf(media!!.fragments.animeFragment.status!!.name),
        bannerUrl = media?.fragments?.animeFragment?.bannerImage,
        synopsis = media?.fragments?.animeFragment?.description,
        genres = media?.fragments?.animeFragment?.genres?.filterNotNull() ?: emptyList(),
        synonyms = media?.fragments?.animeFragment?.synonyms?.asSequence()
            ?.plus(media?.fragments?.animeFragment?.title?.romaji)
            ?.plus(media?.fragments?.animeFragment?.title?.english)
            ?.plus(media?.fragments?.animeFragment?.title?.native_)?.filterNotNull()?.distinct()
            ?.toList()
            ?: emptyList(),
        averageScore = media?.fragments?.animeFragment?.averageScore,
        coverUrl = media?.fragments?.animeFragment?.coverImage?.medium,
        episodes = media?.fragments?.animeFragment?.episodes,
        nextEpisode = media?.fragments?.animeFragment?.nextAiringEpisode?.episode
    )
)