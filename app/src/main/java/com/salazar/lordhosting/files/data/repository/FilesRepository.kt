package com.salazar.lordhosting.files.data.repository

import com.salazar.lordhosting.files.domain.models.File

interface FilesRepository {
    suspend fun listFiles(
        serverID: String,
        directory: String? = null,
    ): Result<List<File>>

    suspend fun createFolder(
        serverID: String,
        root: String,
        folderName: String,
    ): Result<Unit>

    suspend fun writeFile(
        serverID: String,
        file: String,
        content: String,
    ): Result<Unit>

    suspend fun getFileContent(
        serverID: String,
        file: String,
    ): Result<String>

    suspend fun deleteFiles(
        serverID: String,
        root: String,
        files: List<String>,
    ): Result<Unit>
}