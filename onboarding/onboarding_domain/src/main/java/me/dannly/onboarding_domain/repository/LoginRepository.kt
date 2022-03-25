package me.dannly.onboarding_domain.repository

interface LoginRepository {

    suspend fun getUserID(): Result<Int>
    suspend fun getAccessToken(code: String): String?
}