package com.salazar.lordhosting.account.data.request

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UpdatePasswordRequest(
    val current_password: String,
    val password: String,
    val password_confirmation: String,
)
