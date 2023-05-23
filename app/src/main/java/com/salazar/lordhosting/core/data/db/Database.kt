package com.salazar.lordhosting.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.salazar.lordhosting.server.data.db.ServerDao
import com.salazar.lordhosting.server.domain.models.Server
import com.salazar.lordhosting.users.data.db.UserDao
import com.salazar.lordhosting.users.domain.models.User


@Database(
    entities = [
        Server::class,
        User::class,
    ],
    version = 2,
    exportSchema = false,
    autoMigrations = []
)
abstract class Database : RoomDatabase() {
    abstract fun serverDao(): ServerDao
    abstract fun userDao(): UserDao
}