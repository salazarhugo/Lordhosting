package com.salazar.lordhosting.server.data.db

import androidx.room.*
import com.salazar.lordhosting.server.domain.models.Server
import kotlinx.coroutines.flow.Flow

@Dao
interface ServerDao {

    @Query("SELECT * FROM servers WHERE uuid = :serverID")
    fun getServer(serverID: String): Flow<Server>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(server: Server): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(servers: List<Server>)

    @Update
    suspend fun update(sever: Server)

    @Query("DELETE FROM servers")
    suspend fun clearAll()
}
