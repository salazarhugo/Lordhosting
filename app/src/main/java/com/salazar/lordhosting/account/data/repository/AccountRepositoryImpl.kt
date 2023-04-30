package com.salazar.lordhosting.account.data.repository

import com.salazar.lordhosting.account.data.mapper.toAccount
import com.salazar.lordhosting.account.data.request.UpdateEmailRequest
import com.salazar.lordhosting.account.data.request.UpdatePasswordRequest
import com.salazar.lordhosting.account.data.response.AccountAttributes
import com.salazar.lordhosting.account.domain.models.Account
import com.salazar.lordhosting.auth.data.api.PterodactylApi
import com.salazar.lordhosting.auth.data.request.LoginRequest
import com.salazar.lordhosting.core.data.datastore.DataStoreRepositoryImpl
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val api: PterodactylApi,
): AccountRepository {

    override suspend fun getAccountDetails(): Result<Account> {
        return try {
            val account = api.getAccountDetails().toAccount()
            Result.success(account)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun updatePassword(
        currentPassword: String,
        newPassword: String
    ): Result<Unit> {
        return try {
            val request = UpdatePasswordRequest(
                current_password = currentPassword,
                password = newPassword,
                password_confirmation = newPassword,
            )
            api.updatePassword(request = request)
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun updateEmail(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            val request = UpdateEmailRequest(
                email = email,
                password = password,
            )
            api.updateEmail(request = request)
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}

