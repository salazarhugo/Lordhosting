package com.salazar.lordhosting.account.data.repository

import com.salazar.lordhosting.account.domain.models.Account

interface AccountRepository {
    suspend fun getAccountDetails(): Result<Account>

    suspend fun updatePassword(
        currentPassword: String,
        newPassword: String,
    ): Result<Unit>

    suspend fun updateEmail(
        email: String,
        password: String,
    ): Result<Unit>
}