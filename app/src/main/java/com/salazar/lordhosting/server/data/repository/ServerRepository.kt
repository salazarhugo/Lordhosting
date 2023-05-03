package com.salazar.lordhosting.server.data.repository

import com.salazar.lordhosting.server.data.response.ConsoleWebSocketData
import com.salazar.lordhosting.server.data.response.ServerResponse
import com.salazar.lordhosting.server.domain.models.Server
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
}