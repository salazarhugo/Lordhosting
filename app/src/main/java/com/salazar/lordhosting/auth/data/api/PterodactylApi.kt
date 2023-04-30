package com.salazar.lordhosting.auth.data.api

import com.salazar.lordhosting.account.data.request.UpdateEmailRequest
import com.salazar.lordhosting.account.data.request.UpdatePasswordRequest
import com.salazar.lordhosting.account.data.response.AccountDetailsResponse
import com.salazar.lordhosting.auth.data.request.LoginRequest
import com.salazar.lordhosting.auth.data.response.LoginResponse
import com.salazar.lordhosting.server.data.response.ListServersResponse
import retrofit2.Response
import retrofit2.http.*


interface PterodactylApi {

    @GET("sanctum/csrf-cookie")
    suspend fun csrf(): LoginResponse

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): LoginResponse

    @POST("auth/logout")
    suspend fun logout(): Response<Void>

    @GET("api/client")
    suspend fun listServers(
    ): ListServersResponse

    /*
        Account
     */
    @GET("api/client/account")
    suspend fun getAccountDetails(): AccountDetailsResponse

    @PUT("api/client/account/email")
    suspend fun updateEmail(
        @Body request: UpdateEmailRequest,
    ): Response<Void>

    @PUT("api/client/account/password")
    suspend fun updatePassword(
        @Body request: UpdatePasswordRequest,
    ): Response<Void>

    companion object {
//         const val BASE_URL = "https://game.lordhosting.fr/"
        const val BASE_URL = "http://154.51.39.199/"
     }
}