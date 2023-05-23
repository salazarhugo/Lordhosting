package com.salazar.lordhosting.users.data.request

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateUserRequest(
    val email: String,
    val permissions: List<String>,
)
