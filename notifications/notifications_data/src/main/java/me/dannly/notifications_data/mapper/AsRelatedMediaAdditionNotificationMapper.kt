package me.dannly.notifications_data.mapper

import me.dannly.graphql.NotificationQuery
import me.dannly.graphql.type.MediaFormat
import me.dannly.notifications_domain.model.RelatedMediaAdditionNotification

fun NotificationQuery.AsRelatedMediaAdditionNotification.toRelatedMediaAdditionNotification() =
    RelatedMediaAdditionNotification(
        createdAt = createdAt,
        mediaTitle = media!!.fragments.animeFragment.title!!.userPreferred!!,
        mediaId = media!!.fragments.animeFragment.id,
        mediaImage = media?.fragments?.animeFragment?.coverImage?.medium,
        isTv = media!!.fragments.animeFragment.format!! == MediaFormat.TV
    )