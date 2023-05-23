package com.salazar.lordhosting.users.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListUsersResponse(
    val data: List<UserResponse>
)

@JsonClass(generateAdapter = true)
data class UserResponse(
    val attributes: UserAttributes,
)

@JsonClass(generateAdapter = true)
data class UserAttributes(
    val uuid: String,
    val username: String,
    val email: String,
    val image: String,
    val created_at: String,
    @Json(name = "2fa_enabled")
    val twoFactorEnabled: Boolean,
    val permissions: List<String>,
)
