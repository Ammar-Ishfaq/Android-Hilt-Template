package com.pkgname.appname.utils

import com.pkgname.appname.backend.ApiService
import com.pkgname.appname.models.requestModels.login.LoginModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharedWebServices(
    private val apiServices: ApiService
//    private val app: App
) {
    private val dispatcher = Dispatchers.IO

    //Login User
    suspend fun loginUser(
        email: String,
        password: String
    ) = withContext(dispatcher) {
        val body = LoginModel(email, password)
        safeApiCall { Result.success(apiServices.loginUser(body)) }
    }

    suspend fun logoutUser(
        user_id: String
    ) = withContext(dispatcher) {
        safeApiCall { Result.success(apiServices.logoutUser(user_id)) }
    }


}