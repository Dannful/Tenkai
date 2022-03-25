package me.dannly.profile_presentation

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import me.dannly.profile_domain.model.RetrievedShow
import me.dannly.profile_domain.model.RetrievedUser
import me.dannly.profile_domain.model.activity.RetrievedActivity

data class ProfileState(
    val user: RetrievedUser? = null,
    val favourites: Flow<PagingData<RetrievedShow>> = emptyFlow(),
    val activities: Flow<PagingData<RetrievedActivity>> = emptyFlow()
)
