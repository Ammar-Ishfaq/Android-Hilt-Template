package com.codecoy.labourconnect.models.responseModel.login

data class Data(
    val created_at: String,
    val id: Int,
    val token: String,
    val u_dob: String,
    val u_email: String,
    val u_name: String,
    val u_pass: String,
    val u_phone: String,
    val u_uname: String,
    val updated_at: String
)