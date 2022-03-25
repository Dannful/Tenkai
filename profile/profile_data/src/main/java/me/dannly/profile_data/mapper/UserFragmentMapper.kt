package me.dannly.profile_data.mapper

import me.dannly.graphql.fragment.UserFragment
import me.dannly.profile_domain.model.RetrievedGenre
import me.dannly.profile_domain.model.RetrievedScore
import me.dannly.profile_domain.model.RetrievedStudio
import me.dannly.profile_domain.model.RetrievedUser

fun UserFragment.toRetrievedUser() = RetrievedUser(
    id = id,
    name = name,
    imageUrl = avatar?.medium,
    bannerUrl = bannerImage,
    description = about,
    createdAt = createdAt,
    meanScore = statistics?.anime?.meanScore,
    episodesWatched = statistics?.anime?.episodesWatched,
    favouriteGenres = statistics?.anime?.genres?.filterNotNull()?.filter { it.genre != null }
        ?.map { RetrievedGenre(it.genre!!, it.count) }
        ?: emptyList(),
    scores = statistics?.anime?.scores?.filterNotNull()?.filter { it.score != null }
        ?.map { RetrievedScore(it.score!!, it.count) } ?: emptyList(),
    studios = statistics?.anime?.studios?.filterNotNull()?.filter {
        it.studio?.name != null
    }?.map { RetrievedStudio(it.studio!!.name, it.count) } ?: emptyList(),
    minutesWatched = statistics?.anime?.minutesWatched
)