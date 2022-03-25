package me.dannly.notifications_domain.model

data class AiringNotification(
    val episode: Int,
    val createdAt: Int?,
    val mediaId: Int,
    val mediaTitle: String,
    val mediaImage: String?
)
