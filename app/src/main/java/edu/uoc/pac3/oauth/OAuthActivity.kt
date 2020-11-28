package edu.uoc.pac3.oauth

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import edu.uoc.pac3.R
import edu.uoc.pac3.data.TwitchApiService
import edu.uoc.pac3.data.network.Endpoints
import edu.uoc.pac3.data.network.Network.createHttpClient
import edu.uoc.pac3.data.oauth.OAuthConstants.clientID
import edu.uoc.pac3.data.oauth.OAuthConstants.redirectUri
import edu.uoc.pac3.data.oauth.OAuthConstants.scopes
import edu.uoc.pac3.data.oauth.OAuthConstants.uniqueState
import edu.uoc.pac3.data.oauth.OAuthTokensResponse
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import kotlinx.android.synthetic.main.activity_oauth.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class OAuthActivity : AppCompatActivity() {

    private val TAG = "OAuthActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oauth)
        launchOAuthAuthorization()
    }

    fun buildOAuthUri(): Uri {
        return Uri.parse(Endpoints.authorizationUrl)
                .buildUpon()
                .appendQueryParameter("client_id", clientID)
                .appendQueryParameter("redirect_uri", redirectUri)
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("scope", scopes.joinToString(separator = " "))
                .appendQueryParameter("state", uniqueState)
                .build()
    }

    private fun launchOAuthAuthorization() {
        //  Create URI
        val uri = buildOAuthUri()

        // Set webView Redirect Listener
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                request?.let {
                    // Check if this url is our OAuth redirect, otherwise ignore it
                    if (request.url.toString().startsWith(redirectUri)) {
                        // To prevent CSRF attacks, check that we got the same state value we sent, otherwise ignore it
                        val responseState = request.url.getQueryParameter("state")
                        if (responseState == uniqueState) {
                            // This is our request, obtain the code!
                            request.url.getQueryParameter("code")?.let { code ->
                                Log.d("OAuth", "Here is the authorization code! $code")
                                onAuthorizationCodeRetrieved(code)
                            } ?: run {
                                // User cancelled the login flow
                                // TODO: Handle error
                            }
                        }
                    }
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        // Load OAuth Uri
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(uri.toString())
    }

    // Call this method after obtaining the authorization code
    // on the WebView to obtain the tokens
    private fun onAuthorizationCodeRetrieved(authorizationCode: String) {

        // Show Loading Indicator
        progressBar.visibility = View.VISIBLE

        val twitch = TwitchApiService(createHttpClient(this))

        GlobalScope.launch {
            val tokens: OAuthTokensResponse? = twitch.getTokens(authorizationCode)
            if (tokens != null){
                Log.d("OAuth", "About to save tokens ${tokens.accessToken}")
                // TODO: Save access token and refresh token using the SessionManager class
            }
        }

    }
}