package com.salazar.lordhosting.server.data.repository

import com.salazar.lordhosting.server.data.response.ConsoleWebSocketData
import com.salazar.lordhosting.server.data.response.ServerResponse
import com.salazar.lordhosting.server.domain.models.Backup
import com.salazar.lordhosting.server.domain.models.Server
import com.salazar.lordhosting.server.domain.models.Startup
import kotlinx.coroutines.flow.Flow

interface ServerRepository {
    suspend fun listServers(): Result<List<Server>>

    fun getServer(
        serverID: String
    ): Flow<Server>

    suspend fun getWebSocketData(
        serverUUID: String,
    ): Result<ConsoleWebSocketData>

    suspend fun sendCommand(
        serverID: String,
        command: String,
    ): Result<Unit>

    suspend fun updatePowerState(
        serverID: String,
        signal: String,
    ): Result<Unit>

    suspend fun getStartup(
        serverID: String,
    ): Result<Startup>

    suspend fun listBackups(
        serverID: String,
    ): Result<List<Backup>>

    suspend fun createBackup(
        serverID: String,
    ): Result<Backup>

    suspend fun deleteBackup(
        serverID: String,
        backupID: String,
    ): Result<Unit>
}