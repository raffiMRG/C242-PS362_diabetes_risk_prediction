package com.capstone.diabticapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    val riskScore: String,
    val classification: String,
    val createdAt: String
)