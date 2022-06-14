package com.codecoy.labourconnect.models.responseModel.jobofDayModel

data class JobofDayModel(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)