package com.salazar.lordhosting.users.domain.models;

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val uuid: String,
    val username: String,
    val email: String,
    val image: String,
    val createdAt: String,
    val twoFactorEnabled: Boolean,
    val permissionCount: Int,
)
