package com.pkgname.appname.models.responseModel.currentWeekModel

data class Data(
    val `break`: String,
    val done_job_id: String,
    val finish: String,
    val j_date: String,
    val start: String,
    val submission_status: Boolean,
    val working_day: String,
    val working_hours: Double
)