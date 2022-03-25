package me.dannly.profile_domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.dannly.profile_domain.model.RetrievedShow
import me.dannly.profile_domain.model.RetrievedUser
import me.dannly.profile_domain.model.activity.RetrievedActivity

interface ProfileRepository {

    suspend fun searchUser(userId: Int): Result<RetrievedUser>
    suspend fun searchUserFavourites(userId: Int, page: Int): Result<List<RetrievedShow>>
    suspend fun getUserActivity(userId: Int, page: Int): Result<List<RetrievedActivity>>
    suspend fun deleteActivity(id: Int): Result<Boolean>

    fun getPaginatedUserActivity(userId: Int): Flow<PagingData<RetrievedActivity>>
    fun getPaginatedUserFavourites(userId: Int): Flow<PagingData<RetrievedShow>>
}