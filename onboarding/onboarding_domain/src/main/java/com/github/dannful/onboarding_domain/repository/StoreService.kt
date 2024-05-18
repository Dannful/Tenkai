package com.github.dannful.onboarding_domain.repository

interface StoreService {

    suspend fun saveAccessToken(token: String)
    suspend fun saveUserId(userId: Int)
}