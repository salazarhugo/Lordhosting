package com.salazar.lordhosting.server.data.response

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ListServersResponse(
    val data: List<Server>
)

@JsonClass(generateAdapter = true)
data class Server(
    val attributes: ServerAttributes,
)

@JsonClass(generateAdapter = true)
data class ServerAttributes(
    val name: String,
    val uuid: String,
    val node: String,
    val description: String,
    val sftp_details: SftpDetails,
)

@JsonClass(generateAdapter = true)
data class SftpDetails(
    val ip: String,
    val port: Int,
)
