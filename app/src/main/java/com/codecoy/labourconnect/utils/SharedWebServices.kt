package com.codecoy.labourconnect.utils

import com.codecoy.labourconnect.App
import com.codecoy.labourconnect.backend.ApiService
import com.codecoy.labourconnect.models.requestModels.login.LoginModel
import com.codecoy.labourconnect.models.requestModels.submit.SubmitRequestModel
import com.codecoy.labourconnect.models.responseModel.login.LoginModelResponse
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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