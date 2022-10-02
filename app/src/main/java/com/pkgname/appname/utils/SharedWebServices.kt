package com.pkgname.appname.utils

import com.pkgname.appname.backend.ApiService
import com.pkgname.appname.models.requestModels.login.LoginModel
import com.pkgname.appname.models.requestModels.submit.SubmitRequestModel
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

    suspend fun profileData(
        user_id: String
    ) = withContext(dispatcher) {
        safeApiCall { Result.success(apiServices.profileUser(user_id)) }
    }
    suspend fun logoutUser(
        user_id: String
    ) = withContext(dispatcher) {
        safeApiCall { Result.success(apiServices.logoutUser(user_id)) }
    }

    suspend fun jobofDay(
        user_id: String
    ) = withContext(dispatcher) {
        safeApiCall { Result.success(apiServices.jotofDay(user_id)) }
    }

    suspend fun doneJob(
        user_id: String
    ) = withContext(dispatcher) {
        safeApiCall { Result.success(apiServices.doneJob(user_id)) }
    }

    suspend fun currentWeek(
        user_id: String
    ) = withContext(dispatcher) {
        safeApiCall { Result.success(apiServices.currentWeek(user_id)) }
    }

    suspend fun previousWeek(
        user_id: String
    ) = withContext(dispatcher) {
        safeApiCall { Result.success(apiServices.previousWeek(user_id)) }
    }


    suspend fun submit(
        submitRequestModel: SubmitRequestModel
    ) = withContext(dispatcher) {
        safeApiCall { Result.success(apiServices.submit(submitRequestModel)) }
    }




}