package com.salazar.lordhosting.server.data.repository

import com.salazar.lordhosting.server.data.response.Server

interface ServerRepository {
    suspend fun listServers(): Result<List<Server>>
}