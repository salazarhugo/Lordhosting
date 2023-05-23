package com.salazar.lordhosting.auth.data.api

import com.salazar.lordhosting.account.data.request.UpdateEmailRequest
import com.salazar.lordhosting.account.data.request.UpdatePasswordRequest
import com.salazar.lordhosting.account.data.response.AccountDetailsResponse
import com.salazar.lordhosting.auth.data.request.LoginRequest
import com.salazar.lordhosting.auth.data.response.LoginResponse
import com.salazar.lordhosting.core.data.response.GetPermissionsResponse
import com.salazar.lordhosting.files.data.request.CreateFolderRequest
import com.salazar.lordhosting.files.data.request.DeleteFileRequest
import com.salazar.lordhosting.files.data.response.ListFilesResponse
import com.salazar.lordhosting.server.data.request.SendCommandRequest
import com.salazar.lordhosting.server.data.request.UpdatePowerStateRequest
import com.salazar.lordhosting.server.data.response.BackupResponse
import com.salazar.lordhosting.server.data.response.ConsoleWebSocketResponse
import com.salazar.lordhosting.server.data.response.ListBackupsResponse
import com.salazar.lordhosting.server.data.response.ListServersResponse
import com.salazar.lordhosting.server.data.response.ListVariablesResponse
import com.salazar.lordhosting.users.data.request.CreateUserRequest
import com.salazar.lordhosting.users.data.response.ListUsersResponse
import com.salazar.lordhosting.users.data.response.UserResponse
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


    @GET("api/client/servers/{serverUUID}/websocket")
    suspend fun getWebSocket(
        @Path("serverUUID") serverUUID: String,
    ): ConsoleWebSocketResponse

    @POST("api/client/servers/{serverID}/command")
    suspend fun sendCommand(
        @Path("serverID") serverID: String,
        @Body request: SendCommandRequest,
    ): Response<Void>

    @POST("api/client/servers/{serverID}/power")
    suspend fun updatePowerState(
        @Path("serverID") serverID: String,
        @Body request: UpdatePowerStateRequest,
    ): Response<Void>

    @GET("api/client/servers/{serverID}/files/list")
    suspend fun listFiles(
        @Path("serverID") serverID: String,
        // URL encoded path to list files from
        @Query("directory") directory: String? = null,
    ): ListFilesResponse

    @POST("api/client/servers/{serverID}/files/create-folder")
    suspend fun createFolder(
        @Path("serverID") serverID: String,
        @Body request: CreateFolderRequest,
    ): Response<Void>

    @POST("api/client/servers/{serverID}/files/delete")
    suspend fun deleteFile(
        @Path("serverID") serverID: String,
        @Body request: DeleteFileRequest,
    ): Response<Void>

    @GET("api/client/servers/{serverID}/files/contents")
    suspend fun getFileContent(
        @Path("serverID") serverID: String,
        @Query("file") file: String,
    ): Response<String>

    @POST("api/client/servers/{serverID}/files/write")
    suspend fun writeFile(
        @Path("serverID") serverID: String,
        @Query("file") file: String,
        @Body content: String,
    ): Response<Void>

    @GET("api/client/permissions")
    suspend fun getPermissions(): GetPermissionsResponse

    @GET("api/client/servers/{serverID}/users")
    suspend fun listUsers(
        @Path("serverID") serverID: String,
    ): ListUsersResponse

    @POST("api/client/servers/{serverID}/users")
    suspend fun createUser(
        @Path("serverID") serverID: String,
        @Body request: CreateUserRequest,
    ): UserResponse

    @GET("api/client/servers/{serverID}/startup")
    suspend fun listVariables(
        @Path("serverID") serverID: String,
    ): ListVariablesResponse

    @GET("api/client/servers/{serverID}/backups")
    suspend fun listBackups(
        @Path("serverID") serverID: String,
    ): ListBackupsResponse

    @POST("api/client/servers/{serverID}/backups")
    suspend fun createBackup(
        @Path("serverID") serverID: String,
    ): BackupResponse

    @DELETE("api/client/servers/{serverID}/backups/{backupID}")
    suspend fun deleteBackup(
        @Path("serverID") serverID: String,
        @Path("backupID") backupID: String,
    ): Response<Void>

    @DELETE("api/client/servers/{serverID}/users/{userID}")
    suspend fun deleteUser(
        @Path("serverID") serverID: String,
        @Path("userID") userID: String,
    ): Response<Void>

    companion object {
        //         const val BASE_URL = "https://game.lordhosting.fr/"
        const val BASE_URL = "http://154.51.39.199/"
    }
}