package me.dannly.onboarding_data.repository

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import me.dannly.core.BuildConfig
import me.dannly.core.domain.coroutines.DispatcherProvider
import me.dannly.graphql.CurrentlyAuthenticatedUserQuery
import me.dannly.onboarding_domain.repository.LoginRepository
import okhttp3.*
import org.json.JSONObject

@OptIn(
    ExperimentalCoroutinesApi::class
)
class LoginRepositoryImpl(
    private val authenticatedApolloClient: Flow<ApolloClient?>,
    private val dispatcherProvider: DispatcherProvider
) : LoginRepository {

    override suspend fun getUserID() = withContext(dispatcherProvider.io) {
        try {
            Result.success(
                authenticatedApolloClient.filterNotNull().first()
                    .query(CurrentlyAuthenticatedUserQuery())
                    .await().data!!.viewer!!.id
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAccessToken(code: String): String? =
        withContext(dispatcherProvider.io) {
            try {
                val response = OkHttpClient().newCall(getTokenRequest(code)).execute()
                val body = response.body() ?: run {
                    response.close()
                    return@withContext null
                }
                val token = JSONObject(
                    body.string() ?: run {
                        response.close()
                        body.close()
                        return@withContext null
                    }
                ).getString("access_token")
                response.close()
                body.close()
                return@withContext token
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return@withContext null
        }

    private fun getAccessTokenJSON(code: String): String {
        return JSONObject().apply {
            put("grant_type", "authorization_code")
            put("client_id", BuildConfig.CLIENT_ID.toString())
            put("client_secret", BuildConfig.CLIENT_SECRET)
            put("redirect_uri", BuildConfig.CLIENT_REDIRECT_URI)
            put("code", code)
        }.toString()
    }

    private suspend fun getTokenRequest(code: String): Request =
        withContext(dispatcherProvider.io) {
            return@withContext Request.Builder().header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .url("https://anilist.co/api/v2/oauth/token")
                .post(
                    RequestBody.create(
                        MediaType.parse("application/json; charset=utf-8"),
                        getAccessTokenJSON(code)
                    )
                ).build()
        }
}