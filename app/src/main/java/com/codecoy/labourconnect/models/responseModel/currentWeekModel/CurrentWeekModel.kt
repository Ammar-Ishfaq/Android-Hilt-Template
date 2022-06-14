package com.codecoy.labourconnect.models.responseModel.currentWeekModel

data class CurrentWeekModel(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)