package edu.uoc.pac3.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by alex on 06/09/2020.
 */

class SessionManager(context: Context) {

    companion object{
        const val accessTokenKey = "ACCESS_TOKEN"
        const val refreshTokenKey = "REFRESH_TOKEN"
    }

    private var sharedPreferences: SharedPreferences = context.getSharedPreferences("PREFERENCES_FILE", Context.MODE_PRIVATE)

    fun isUserAvailable(): Boolean {
        return getAccessToken() != null
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(accessTokenKey, null)
    }

    fun saveAccessToken(accessToken: String) {
        sharedPreferences
            .edit()
            .putString(accessTokenKey, accessToken)
            .apply()
    }

    fun clearAccessToken() {
        sharedPreferences
            .edit()
            .putString(accessTokenKey, null)
            .apply()
    }

    fun getRefreshToken(): String? {
        return sharedPreferences.getString(refreshTokenKey, null)
    }

    fun saveRefreshToken(refreshToken: String) {
        sharedPreferences
            .edit()
            .putString(refreshTokenKey, refreshToken)
            .apply()
    }

    fun clearRefreshToken() {
        sharedPreferences
            .edit()
            .putString(refreshTokenKey, null)
            .apply()
    }

}