package com.salazar.lordhosting.server.data.response

import com.squareup.moshi.JsonClass


sealed class WebSocketEvent {
    @JsonClass(generateAdapter = true)
    data class Stats(
        val memory_bytes: Long,
        val memory_limit_bytes: Long,
        val cpu_absolute: Double,
        val network: NetworkStats,
        val state: String,
        val disk_bytes: Long
    ): WebSocketEvent()

    @JsonClass(generateAdapter = true)
    data class ConsoleOutput(
        val output: String,
    ): WebSocketEvent()
}

@JsonClass(generateAdapter = true)
data class EventResponse(
    val event: String,
    val args: List<String>?,
)

@JsonClass(generateAdapter = true)
data class AuthSuccess(val event: String)

@JsonClass(generateAdapter = true)
data class StatusUpdate(val event: String, val args: List<String>)


@JsonClass(generateAdapter = true)
data class TokenExpiring(val event: String)

@JsonClass(generateAdapter = true)
data class TokenExpired(val event: String)

@JsonClass(generateAdapter = true)
data class NetworkStats(val rx_bytes: Long, val tx_bytes: Long)
