package com.salazar.lordhosting.server.domain.models;

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "servers")
data class Server(
    @PrimaryKey
    val uuid: String,
    val name: String,
    val identifier: String,
    val node: String,
    val description: String,
    val ip: String,
    val port: Int,
)
