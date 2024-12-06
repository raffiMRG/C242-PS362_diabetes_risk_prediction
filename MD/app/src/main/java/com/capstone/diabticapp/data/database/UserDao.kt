package com.capstone.diabticapp.data.database

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE name = :name")
    suspend fun getAllUsers(name: String): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Query("DELETE FROM users WHERE name = :name")
    suspend fun deleteAll(name: String)
}