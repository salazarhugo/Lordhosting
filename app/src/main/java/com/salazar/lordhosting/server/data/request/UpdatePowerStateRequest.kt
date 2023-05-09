package com.salazar.lordhosting.server.data.request

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class UpdatePowerStateRequest(
    val signal: String,
)