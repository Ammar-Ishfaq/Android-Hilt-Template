package com.codecoy.labourconnect.backend

import com.codecoy.labourconnect.backend.BASE_URL.TOKEN
import com.codecoy.labourconnect.models.requestModels.login.LoginModel
import com.codecoy.labourconnect.models.requestModels.submit.SubmitRequestModel
import com.codecoy.labourconnect.models.responseModel.currentWeekModel.CurrentWeekModel
import com.codecoy.labourconnect.models.responseModel.donejob.DoneJobModel
import com.codecoy.labourconnect.models.responseModel.jobofDayModel.JobofDayModel
import com.codecoy.labourconnect.models.responseModel.login.LoginModelResponse
import com.codecoy.labourconnect.models.responseModel.logout.LogoutResponseModel
import com.codecoy.labourconnect.models.responseModel.profile.ProfileModel
import com.codecoy.labourconnect.utils.SubmitResposeModel
import retrofit2.http.*


interface ApiService {

    @POST("login")
    suspend fun loginUser(@Body body: LoginModel): LoginModelResponse

    @POST("work/submit")
    suspend fun submit(@Body body: SubmitRequestModel,@Header("Authorization") token: String=TOKEN): SubmitResposeModel

    @GET("profile/show")
    suspend fun profileUser( @Query("user_id") user_id: String,@Header("Authorization") token: String=TOKEN): ProfileModel

    @GET("work/job/done/details")
    suspend fun doneJob( @Query("done_job_id") user_id: String,@Header("Authorization") token: String=TOKEN): DoneJobModel


    @POST("logout")
    suspend fun logoutUser( @Query("user_id") user_id: String,@Header("Authorization") token: String=TOKEN): LogoutResponseModel

    @GET("work/time_sheet/current")
    suspend fun currentWeek( @Query("user_id") user_id: String,@Header("Authorization") token: String=TOKEN): CurrentWeekModel

    @GET("work/time_sheet/previous")
    suspend fun previousWeek( @Query("user_id") user_id: String,@Header("Authorization") token: String=TOKEN): CurrentWeekModel


    @GET("work/get_today_jobs")
    suspend fun jotofDay( @Query("user_id") user_id: String,@Header("Authorization") token: String=TOKEN): JobofDayModel

}