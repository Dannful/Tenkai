package me.dannly.notifications_data.mapper

import me.dannly.graphql.NotificationQuery
import me.dannly.notifications_domain.model.ActivityLikeNotification

fun NotificationQuery.AsActivityLikeNotification.toActivityLikeNotification() =
    ActivityLikeNotification(
        createdAt = createdAt,
        userName = user!!.fragments.userFragment.name,
        userAvatar = user?.fragments?.userFragment?.avatar?.medium,
        userId = user!!.fragments.userFragment.id
    )