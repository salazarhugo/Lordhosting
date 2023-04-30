package com.salazar.lordhosting.auth.data.response

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class LoginResponse(
    val status: Int?
)
