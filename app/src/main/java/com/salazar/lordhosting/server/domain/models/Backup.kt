package com.salazar.lordhosting.server.domain.models;

import java.util.Date

data class Backup(
    val uuid: String,
    val name: String,
    val ignored_files: List<String>,
    val sha256_hash: String,
    val bytes: Int,
    val created_at: Date?,
    val completed_at: Date?,
    val checksum: String,
)
