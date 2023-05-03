package com.salazar.lordhosting.server.data.response

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ConsoleWebSocketResponse(
    val data: ConsoleWebSocketData,
)

@JsonClass(generateAdapter = true)
data class ConsoleWebSocketData(
    val token: String,
    val socket: String,
)
