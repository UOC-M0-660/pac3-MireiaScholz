package edu.uoc.pac3.data

import android.util.Log
import edu.uoc.pac3.data.network.Endpoints
import edu.uoc.pac3.data.oauth.OAuthAccessTokenResponse
import edu.uoc.pac3.data.oauth.OAuthConstants.clientID
import edu.uoc.pac3.data.oauth.OAuthConstants.clientSecret
import edu.uoc.pac3.data.oauth.OAuthConstants.redirectUri
import edu.uoc.pac3.data.oauth.OAuthTokensResponse
import edu.uoc.pac3.data.oauth.UnauthorizedException
import edu.uoc.pac3.data.streams.StreamsResponse
import edu.uoc.pac3.data.user.User
import edu.uoc.pac3.data.user.UserResponse
import io.ktor.client.*
import io.ktor.client.request.*

/**
 * Created by alex on 24/10/2020.
 */

class TwitchApiService(private val httpClient: HttpClient) {
    private val TAG = "TwitchApiService"

    /// Gets Access and Refresh Tokens on Twitch
    suspend fun getTokens(authorizationCode: String): OAuthTokensResponse? {
        val response: OAuthAccessTokenResponse? = httpClient.post(Endpoints.tokens) {
            parameter("client_id", clientID)
            parameter("client_secret", clientSecret)
            parameter("code", authorizationCode)
            parameter("grant_type", "authorization_code")
            parameter("redirect_uri", redirectUri)
        }

        if (response == null) {
            return response
        }

        Log.d("OAuth", "Access Token: ${response.accessToken}")
        Log.d("OAuth", "Refresh Token: ${response.refreshToken}")
        return OAuthTokensResponse(response.accessToken, response.refreshToken)
    }

    /// Gets Streams on Twitch
    @Throws(UnauthorizedException::class)
    suspend fun getStreams(cursor: String? = null): StreamsResponse {
        return httpClient.get(Endpoints.getStreams)
        //TODO("Support Pagination")
    }

    /// Gets Current Authorized User on Twitch
    @Throws(UnauthorizedException::class)
    suspend fun getUser(): User? {
        val userResponse: UserResponse = httpClient.get(Endpoints.getUser)
        Log.d("OAuth", "User profile: ${userResponse.data}")
        return userResponse.data?.get(0)
    }

    /// Gets Current Authorized User on Twitch
    @Throws(UnauthorizedException::class)
    suspend fun updateUserDescription(description: String): User? {
        val userResponse: UserResponse = httpClient.put(Endpoints.getUser + "?description=$description")
        Log.d("OAuth", "User profile updated: ${userResponse.data}")
        return userResponse.data?.get(0)
    }
}