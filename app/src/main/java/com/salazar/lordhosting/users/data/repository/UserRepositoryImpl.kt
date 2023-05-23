package com.salazar.lordhosting.users.data.repository

import com.salazar.lordhosting.auth.data.api.PterodactylApi
import com.salazar.lordhosting.core.data.response.PermissionResponse
import com.salazar.lordhosting.users.data.db.UserDao
import com.salazar.lordhosting.users.data.request.CreateUserRequest
import com.salazar.lordhosting.users.domain.models.User
import kotlinx.coroutines.flow.Flow
import toUser
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val api: PterodactylApi,
): UserRepository {
    override fun listUsers(
        serverID: String,
    ): Flow<List<User>> = userDao.getUsers()

    override suspend fun fetchUsers(
        serverID: String,
    ): Result<List<User>> {
        return try {
            val response = api.listUsers(
                serverID = serverID,
            ).data
            val users = response.map {
                it.toUser()
            }
            userDao.clearAll()
            userDao.insertAll(users)
            Result.success(users)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun createUser(
        serverID: String,
        email: String,
        permissions: List<String>
    ): Result<User> {
        return try {
            val request = CreateUserRequest(
                email = email,
                permissions = permissions,
            )
            val response = api.createUser(
                serverID = serverID,
                request = request,
            )

            val user = response.toUser()
            userDao.insert(user)

            Result.success(user)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun deleteUser(
        serverID: String,
        userID: String,
    ): Result<Unit> {
        return try {
            api.deleteUser(
                serverID = serverID,
                userID = userID,
            )
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun getPermissions(): Result<Map<String, PermissionResponse>> {
        return try {
            val result = api.getPermissions()
            val permissions = result.attributes.permissions
            Result.success(permissions)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}