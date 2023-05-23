package com.salazar.lordhosting.server.data.repository

import com.salazar.lordhosting.auth.data.api.PterodactylApi
import com.salazar.lordhosting.server.data.db.ServerDao
import com.salazar.lordhosting.server.data.mapper.toBackup
import com.salazar.lordhosting.server.data.mapper.toServer
import com.salazar.lordhosting.server.data.mapper.toStartup
import com.salazar.lordhosting.server.data.request.SendCommandRequest
import com.salazar.lordhosting.server.data.request.UpdatePowerStateRequest
import com.salazar.lordhosting.server.data.response.ConsoleWebSocketData
import com.salazar.lordhosting.server.domain.models.Backup
import com.salazar.lordhosting.server.domain.models.Server
import com.salazar.lordhosting.server.domain.models.Startup
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

    override suspend fun getStartup(
        serverID: String,
    ): Result<Startup> {
        return try {
            val result = api.listVariables(
                serverID = serverID,
            )
            Result.success(result.toStartup())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun listBackups(
        serverID: String,
    ): Result<List<Backup>> {
        return try {
            val result = api.listBackups(
                serverID = serverID,
            ).data
            val backups = result.map { it.toBackup() }
            Result.success(backups)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun createBackup(serverID: String): Result<Backup> {
        return try {
            val result = api.createBackup(
                serverID = serverID,
            )
            Result.success(result.toBackup())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun deleteBackup(
        serverID: String,
        backupID: String,
    ): Result<Unit> {
        return try {
            api.deleteBackup(
                serverID = serverID,
                backupID = backupID,
            )
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}