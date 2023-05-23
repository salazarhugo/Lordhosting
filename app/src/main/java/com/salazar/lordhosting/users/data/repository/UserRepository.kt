package com.salazar.lordhosting.users.data.repository

import com.salazar.lordhosting.core.data.response.PermissionResponse
import com.salazar.lordhosting.users.domain.models.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun listUsers(
        serverID: String,
    ): Flow<List<User>>

    suspend fun fetchUsers(
        serverID: String,
    ): Result<List<User>>

    suspend fun createUser(
        serverID: String,
        email: String,
        permissions: List<String>,
    ): Result<User>
    suspend fun deleteUser(
        serverID: String,
        userID: String,
    ): Result<Unit>

    suspend fun getPermissions(): Result<Map<String, PermissionResponse>>
}
