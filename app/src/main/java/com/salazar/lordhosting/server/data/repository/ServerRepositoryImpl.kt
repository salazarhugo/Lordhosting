package com.salazar.lordhosting.server.data.repository

import com.salazar.lordhosting.auth.data.api.PterodactylApi
import com.salazar.lordhosting.server.data.db.ServerDao
import com.salazar.lordhosting.server.data.mapper.toServer
import com.salazar.lordhosting.server.data.request.SendCommandRequest
import com.salazar.lordhosting.server.data.request.UpdatePowerStateRequest
import com.salazar.lordhosting.server.data.response.ConsoleWebSocketData
import com.salazar.lordhosting.server.data.response.ServerResponse
import com.salazar.lordhosting.server.domain.models.Server
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ServerRepositoryImpl @Inject constructor(
    private val serverDao: ServerDao,
    private val api: PterodactylApi,
): ServerRepository {
    override suspend fun listServers(): Result<List<Server>> {
        return try {
            val response = api.listServers().data
            val servers = response.map { it.toServer() }
            serverDao.clearAll()
            serverDao.insertAll(servers)
            Result.success(servers)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override fun getServer(serverID: String): Flow<Server> {
        return serverDao.getServer(serverID = serverID)
    }

    override suspend fun getWebSocketData(
        serverUUID: String,
    ): Result<ConsoleWebSocketData> {
        return try {
            val response = api.getWebSocket(serverUUID = serverUUID)
            Result.success(response.data)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun sendCommand(
        serverID: String,
        command: String
    ): Result<Unit> {
        return try {
            val request = SendCommandRequest(command = command)
            api.sendCommand(
                serverID = serverID,
                request = request,
            )
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun updatePowerState(
        serverID: String,
        signal: String
    ): Result<Unit> {
        return try {
            val request = UpdatePowerStateRequest(signal = signal)
            api.updatePowerState(
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