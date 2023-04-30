package com.salazar.lordhosting.server.data.repository

import com.salazar.lordhosting.auth.data.api.PterodactylApi
import com.salazar.lordhosting.server.data.response.Server
import javax.inject.Inject

class ServerRepositoryImpl @Inject constructor(
    private val api: PterodactylApi,
): ServerRepository {
    override suspend fun listServers(): Result<List<Server>> {
        return try {
            val response = api.listServers()
            Result.success(response.data)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}