package me.dannly.profile_data.mapper

import me.dannly.graphql.ActivityQuery
import me.dannly.profile_domain.model.activity.RetrievedActivity

fun ActivityQuery.Activity.toRetrievedActivity() = RetrievedActivity(
    asListActivity = asListActivity?.toListActivity(),
    asMessageActivity = asMessageActivity?.toMessageActivity()
)