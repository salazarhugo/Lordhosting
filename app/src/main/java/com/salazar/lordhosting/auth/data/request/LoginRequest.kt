package com.salazar.lordhosting.auth.data.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class LoginRequest(
    val user: String,
    val password: String,
    @Json(name = "g-recaptcha-response")
    val gRecaptchaResponse: String,
)
