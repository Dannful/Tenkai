package me.dannly.browse_data.mapper

import me.dannly.browse_domain.model.SearchedUser
import me.dannly.graphql.fragment.UserFragment

fun UserFragment.toSearchedUser() = SearchedUser(
    id = id,
    name = name,
    imageUrl = avatar?.medium,
    description = about,
    createdAt = createdAt
)