package com.pkgname.appname.models.responseModel.currentWeekModel

data class CurrentWeekModel(
    val `data`: List<Data>,
    val message: String,
    val status: Boolean
)