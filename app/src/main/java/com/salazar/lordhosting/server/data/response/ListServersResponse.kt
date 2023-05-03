package com.salazar.lordhosting.server.data.response

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ListServersResponse(
    val data: List<ServerResponse>
)

@JsonClass(generateAdapter = true)
data class ServerResponse(
    val attributes: ServerAttributes,
)

@JsonClass(generateAdapter = true)
data class ServerAttributes(
    val name: String,
    val identifier: String,
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
