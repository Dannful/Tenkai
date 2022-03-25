package me.dannly.notifications_domain.model

data class RelatedMediaAdditionNotification(
    val createdAt: Int?,
    val mediaId: Int,
    val mediaTitle: String,
    val mediaImage: String?,
    val isTv: Boolean
)
