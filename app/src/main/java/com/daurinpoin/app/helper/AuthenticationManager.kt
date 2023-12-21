package com.daurinpoin.app.helper

import android.content.Context
import android.content.SharedPreferences
import com.daurinpoin.app.response.UserModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AuthenticationManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "AuthPrefs"
        private const val KEY_USER = "key_user"
    }

    fun loginUser(user: UserModel) {
        val userJson = Json.encodeToString(user)
        sharedPreferences.edit().putString(KEY_USER, userJson).apply()
    }

    fun getCurrentUser(): UserModel? {
        val userJson = sharedPreferences.getString(KEY_USER, null)
        return if (userJson != null) {
            Json.decodeFromString(userJson)
        } else {
            null
        }
    }

    fun logoutUser() {
        sharedPreferences.edit().remove(KEY_USER).apply()
    }
}

