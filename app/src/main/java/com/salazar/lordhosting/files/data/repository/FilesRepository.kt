package com.salazar.lordhosting.files.data.repository

import com.salazar.lordhosting.files.domain.models.File

interface FilesRepository {
    suspend fun listFiles(
        serverID: String,
        directory: String? = null,
    ): Result<List<File>>
}