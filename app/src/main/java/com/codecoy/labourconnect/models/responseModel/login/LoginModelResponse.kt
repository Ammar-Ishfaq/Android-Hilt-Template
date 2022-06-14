package com.codecoy.labourconnect.models.responseModel.login

data class LoginModelResponse(
    val `data`: Data,
    val message: String,
    val status: Boolean
)