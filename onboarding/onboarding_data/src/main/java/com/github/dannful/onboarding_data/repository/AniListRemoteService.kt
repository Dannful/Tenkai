package com.github.dannful.onboarding_data.repository

import com.apollographql.apollo3.ApolloClient
import com.github.dannful.core.BuildConfig
import com.github.dannful.core.domain.model.coroutines.DispatcherProvider
import com.github.dannful.models.GetViewerIdQuery
import com.github.dannful.onboarding_domain.repository.RemoteService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

class AniListRemoteService(
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider,
    private val apolloClientFlow: Flow<ApolloClient>
) : RemoteService {

    @Serializable
    private data class AccessTokenRequest(
        val grant_type: String,
        val client_id: Int,
        val client_secret: String,
        val code: String,
        val redirect_uri: String
    )

    @Serializable
    private data class AccessTokenResponse(
        val access_token: String
    )

    override suspend fun getAccessToken(code: String): Result<String> =
        withContext(dispatcherProvider.IO) {
            try {
                val queryUrl = "https://anilist.co/api/v2/oauth/token"
                val grantType = "authorization_code"
                val request = httpClient.post(urlString = queryUrl) {
                    contentType(ContentType.Application.Json)
                    setBody(
                        AccessTokenRequest(
                            client_id = BuildConfig.CLIENT_ID,
                            client_secret = BuildConfig.CLIENT_SECRET,
                            code = code,
                            redirect_uri = BuildConfig.CLIENT_REDIRECT_URL,
                            grant_type = grantType
                        )
                    )
                }
                val token = request.body<AccessTokenResponse>().access_token
                httpClient.close()
                Result.success(token)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun getViewerId(): Result<Int> = withContext(dispatcherProvider.IO) {
        try {
            val client = apolloClientFlow.first()
            val response = client.query(GetViewerIdQuery()).execute()
            val id = response.data?.Viewer?.id ?: return@withContext Result.failure(Exception())
            Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}