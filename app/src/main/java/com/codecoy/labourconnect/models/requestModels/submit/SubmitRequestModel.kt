package com.codecoy.labourconnect.models.requestModels.submit

data class SubmitRequestModel(
    val job_id: String,
    val user_id: String,
    val start: String,
    val finish: String,
    val `break`: String,
    val supervisor: String,
    val signature: String
)