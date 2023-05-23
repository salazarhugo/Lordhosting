package com.salazar.lordhosting.users.data.db

import androidx.room.*
import com.salazar.lordhosting.users.domain.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(server: User): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Update
    suspend fun update(sever: User)

    @Query("DELETE FROM servers")
    suspend fun clearAll()
}
