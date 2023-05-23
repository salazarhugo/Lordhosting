package com.salazar.lordhosting.server.data.response

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ListBackupsResponse(
    val data: List<BackupResponse>,
)

@JsonClass(generateAdapter = true)
data class BackupResponse(
    val attributes: BackupsAttributes,
)

@JsonClass(generateAdapter = true)
data class BackupsAttributes(
    val uuid: String,
    val name: String,
    val ignored_files: List<String>,
    val sha256_hash: String?,
    val bytes: Int,
    val created_at: String,
    val completed_at: String?,
    val checksum: String?,
)