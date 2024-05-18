package com.github.dannful.onboarding_domain.repository

interface RemoteService {

    suspend fun getAccessToken(code: String): Result<String>
    suspend fun getViewerId(): Result<Int>
}