package me.dannly.profile_data.mapper

import me.dannly.graphql.ActivityQuery
import me.dannly.profile_domain.model.activity.MessageActivity

fun ActivityQuery.AsMessageActivity.toMessageActivity() = MessageActivity(
    id = id,
    message = message!!,
    senderImage = messenger?.fragments?.userFragment?.avatar?.medium,
    senderName = messenger!!.fragments.userFragment.name,
    senderId = messenger!!.fragments.userFragment.id
)