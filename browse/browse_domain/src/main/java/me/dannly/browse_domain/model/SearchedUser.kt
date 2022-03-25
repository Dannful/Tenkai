package me.dannly.browse_domain.model

data class SearchedUser(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val description: String?,
    val createdAt: Int?
)
