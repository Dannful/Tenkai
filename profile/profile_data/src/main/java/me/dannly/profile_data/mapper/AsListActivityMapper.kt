package me.dannly.profile_data.mapper

import me.dannly.graphql.ActivityQuery
import me.dannly.profile_domain.model.activity.ListActivity

fun ActivityQuery.AsListActivity.toListActivity() = ListActivity(
    id = id,
    progress = progress,
    mediaTitle = media!!.fragments.animeFragment.title!!.userPreferred!!,
    mediaImage = media?.fragments?.animeFragment?.coverImage?.medium,
    status = status,
    mediaId = media!!.fragments.animeFragment.id
)