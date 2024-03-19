package com.pkgname.appname.backend

import com.pkgname.appname.backend.BASE_URL.TOKEN
import com.pkgname.appname.models.requestModels.login.LoginModel
import com.pkgname.appname.models.responseModel.login.LoginModelResponse
import com.pkgname.appname.models.responseModel.logout.LogoutResponseModel
import retrofit2.http.*


interface ApiService {

    @POST("login")
    suspend fun loginUser(@Body body: LoginModel): LoginModelResponse


    @POST("logout")
    suspend fun logoutUser( @Query("user_id") user_id: String,@Header("Authorization") token: String=TOKEN): LogoutResponseModel

}