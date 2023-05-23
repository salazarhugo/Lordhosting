package com.salazar.lordhosting.files.data.repository

import com.salazar.lordhosting.auth.data.api.PterodactylApi
import com.salazar.lordhosting.files.data.mapper.toFile
import com.salazar.lordhosting.files.data.request.CreateFolderRequest
import com.salazar.lordhosting.files.data.request.DeleteFileRequest
import com.salazar.lordhosting.files.domain.models.File
import javax.inject.Inject

class FilesRepositoryImpl @Inject constructor(
    private val api: PterodactylApi,
): FilesRepository {
    override suspend fun listFiles(
        serverID: String,
        directory: String?,
    ): Result<List<File>> {
        return try {
            val response = api.listFiles(
                serverID = serverID,
                directory = directory,
            ).data
            val files = response.map {
                it.toFile()
            }
            Result.success(files)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun createFolder(
        serverID: String,
        root: String,
        folderName: String
    ): Result<Unit> {
        return try {
            val request = CreateFolderRequest(
                root = root.ifBlank { "/" },
                name = folderName,
            )
            api.createFolder(
                serverID = serverID,
                request = request,
            )
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun deleteFiles(
        serverID: String,
        root: String,
        files: List<String>
    ): Result<Unit> {
        return try {
            val request = DeleteFileRequest(
                root = root.ifBlank { "/" },
                files = files,
            )
            api.deleteFile(
                serverID = serverID,
                request = request,
            )
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}