package com.salazar.lordhosting.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.salazar.lordhosting.server.data.db.ServerDao
import com.salazar.lordhosting.server.domain.models.Server


@Database(
    entities = [
        Server::class,
    ],
    version = 1,
    exportSchema = false,
    autoMigrations = []
)
abstract class Database : RoomDatabase() {
    abstract fun serverDao(): ServerDao
}