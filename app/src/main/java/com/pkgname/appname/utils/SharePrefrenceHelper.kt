package com.pkgname.appname.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharePrefrenceHelper private constructor(private val sharedPreferences: SharedPreferences) {

    private val dispatcher = Dispatchers.Default

    companion object {

        private const val USER_SHARED_PREFERENCE =
            "skipshare_preferences"
        private const val PHONE_AUTH = "phone_authentication"
        private const val PHONE_NUMBER = "phone_number"
        private const val LOGIN_DATA = "SharedPreferenceHelper_login_data"
        private const val IS_LOGGED_IN = "SharedPreferenceHelper_is_logged_in"
        private const val USER_ID = "userid"
        private const val LOGIN_EMAIL = "login_email"
        private const val LOGIN_PASS = "login_password"
        private const val TOKEN = "token"
        private const val SAVE_USERNAME = "save_username"




        private var INSTANCE: SharePrefrenceHelper? = null

        fun getInstance(context: Context): SharePrefrenceHelper {

            val sharedPreference =
                context.getSharedPreferences(USER_SHARED_PREFERENCE, Context.MODE_PRIVATE)
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SharePrefrenceHelper(sharedPreference).also { INSTANCE = it }
            }
        }
    }


    private suspend fun put(key: String, value: String) = withContext(dispatcher) {
        sharedPreferences.edit {
            putString(key, value)
        }
    }

    private suspend fun put(key: String, value: Boolean) = withContext(dispatcher) {
        sharedPreferences.edit {
            putBoolean(key, value)
        }
    }


    suspend fun saveToken(token: String){
        put(TOKEN, token)
    }

    fun getToken() : String? {
        return sharedPreferences.getString(TOKEN, null)
    }

    suspend fun saveUserId(id: String) {
        put(USER_ID, id)
    }

    fun getUserId(): String? {
        return sharedPreferences.getString(USER_ID, "")
    }

    suspend fun phoneAuth() {
        put(PHONE_AUTH, String())
    }

    suspend fun savePhoneNumber(phone: String) {
        put(PHONE_NUMBER, phone)
    }

    fun getPhoneNumber(): String? {
        return sharedPreferences.getString(PHONE_NUMBER, "")
    }

    suspend fun saveLoginPassword(password: String) {
        put(LOGIN_PASS, password)
    }

    fun getLoginPassword(): String? {
        return sharedPreferences.getString(LOGIN_PASS, "")
    }

    suspend fun saveLoginEmail(email: String) {
        put(LOGIN_EMAIL, email)
    }

    fun getLoginEmail(): String? {
        return sharedPreferences.getString(LOGIN_EMAIL, "")
    }


    suspend fun saveUserLogIn() {
        put(IS_LOGGED_IN, true)
    }

    suspend fun saveUserLogout() {
        put(IS_LOGGED_IN, false)
    }

    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false)
    }

    suspend fun saveUserName(userName: String){
        put(SAVE_USERNAME, userName)
    }

    fun getUserName(): String?{
        return sharedPreferences.getString(SAVE_USERNAME, null)
    }



}