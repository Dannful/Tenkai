package me.dannly.notifications_data.mapper

import me.dannly.graphql.NotificationQuery
import me.dannly.notifications_domain.model.AiringNotification

fun NotificationQuery.AsAiringNotification.toAiringNotification() = AiringNotification(
    episode = episode,
    createdAt = createdAt,
    mediaTitle = media!!.fragments.animeFragment.title!!.userPreferred!!,
    mediaId = media!!.fragments.animeFragment.id,
    mediaImage = media?.fragments?.animeFragment?.coverImage?.medium
)