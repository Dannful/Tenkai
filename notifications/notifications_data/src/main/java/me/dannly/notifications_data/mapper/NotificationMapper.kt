package me.dannly.notifications_data.mapper

import me.dannly.graphql.NotificationQuery
import me.dannly.notifications_domain.model.RetrievedNotification

fun NotificationQuery.Notification.toRetrievedNotification() = RetrievedNotification(
    activityLikeNotification = asActivityLikeNotification?.toActivityLikeNotification(),
    airingNotification = asAiringNotification?.toAiringNotification(),
    relatedMediaAdditionNotification = asRelatedMediaAdditionNotification?.toRelatedMediaAdditionNotification()
)