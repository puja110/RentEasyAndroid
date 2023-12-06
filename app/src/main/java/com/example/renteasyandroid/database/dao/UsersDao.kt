package com.example.renteasyandroid.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.renteasyandroid.database.entity.UserEntity

@Dao
interface UsersDao {

    // query function to add new user
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: UserEntity)

    // query function to fetch all the users
    @Query("SELECT * FROM users")
    fun getUsers(): List<UserEntity>

    // query function to update a users
    @Update
    suspend fun update(entity: UserEntity)

    // query function to delete a users
    @Delete
    suspend fun delete(entity: UserEntity)
}