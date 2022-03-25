package me.dannly.notifications_domain.model

data class ActivityLikeNotification(
    val createdAt: Int?,
    val userName: String,
    val userAvatar: String?,
    val userId: Int
)
