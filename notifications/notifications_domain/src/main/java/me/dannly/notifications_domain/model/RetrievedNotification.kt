package me.dannly.notifications_domain.model

data class RetrievedNotification(
    val activityLikeNotification: ActivityLikeNotification?,
    val airingNotification: AiringNotification?,
    val relatedMediaAdditionNotification: RelatedMediaAdditionNotification?
)
