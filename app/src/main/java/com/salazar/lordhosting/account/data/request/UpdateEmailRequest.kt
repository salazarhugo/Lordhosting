package com.salazar.lordhosting.account.data.request

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UpdateEmailRequest(
    val email: String,
    val password: String,
)
