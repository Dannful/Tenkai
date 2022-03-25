package me.dannly.profile_domain.model.activity

data class ListActivity(
    val id: Int,
    val progress: String?,
    val mediaTitle: String,
    val mediaImage: String?,
    val mediaId: Int,
    val status: String?
)
