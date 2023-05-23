package com.salazar.lordhosting.server.data.mapper

import com.salazar.lordhosting.server.data.response.BackupResponse
import com.salazar.lordhosting.server.domain.models.Backup
import java.text.SimpleDateFormat
import java.util.Date

fun BackupResponse.toBackup(): Backup {
    attributes.apply {
        return Backup(
            name = name,
            uuid = uuid,
            bytes = bytes,
            completed_at = completed_at?.toDate(),
            created_at = created_at.toDate(),
            ignored_files = ignored_files,
            sha256_hash = sha256_hash ?: "",
            checksum = checksum ?: "",
        )
    }
}

fun String.toDate(): Date? {
    return try {
        val dateString = "2023-05-22T15:32:39+02:00"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
        val date  = dateFormat.parse(dateString)
        date
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
