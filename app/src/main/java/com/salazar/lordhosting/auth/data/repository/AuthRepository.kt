package com.salazar.lordhosting.auth.data.repository

interface AuthRepository {
    suspend fun login(
        username: String,
        password: String,
    ): Result<Unit>

    suspend fun logout(): Result<Unit>
}