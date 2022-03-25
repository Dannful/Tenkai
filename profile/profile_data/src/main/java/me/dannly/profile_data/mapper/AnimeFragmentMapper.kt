package me.dannly.profile_data.mapper

import me.dannly.graphql.fragment.AnimeFragment
import me.dannly.profile_domain.model.RetrievedShow

fun AnimeFragment.toRetrievedShow() = RetrievedShow(
    id = id,
    averageScore = averageScore,
    userPreferredTitle = title!!.userPreferred!!,
    imageUrl = coverImage?.medium
)