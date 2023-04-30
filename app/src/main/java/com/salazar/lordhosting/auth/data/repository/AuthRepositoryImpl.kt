package com.salazar.lordhosting.auth.data.repository

import com.salazar.lordhosting.auth.data.api.PterodactylApi
import com.salazar.lordhosting.auth.data.request.LoginRequest
import com.salazar.lordhosting.core.data.datastore.DataStoreRepositoryImpl
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: PterodactylApi,
): AuthRepository {
    override suspend fun login(
        username: String,
        password: String,
    ): Result<Unit> {
        csrf()
        return try {
            val request = LoginRequest(
                user = username,
                password = password,
                gRecaptchaResponse = "",
            )
            val response = api.login(request = request)
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            api.logout()
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun csrf(): Result<Unit> {
        return try {
            val response = api.csrf()
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}

