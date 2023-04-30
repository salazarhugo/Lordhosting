package com.salazar.lordhosting.account.data.response

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class AccountDetailsResponse(
    val attributes: AccountAttributes
)

@JsonClass(generateAdapter = true)
data class AccountAttributes(
    val id: Int,
    val admin: Boolean,
    val username: String,
    val email: String,
    val first_name: String,
    val last_name: String,
    val language: String,
)
