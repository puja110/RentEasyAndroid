package com.example.renteasyandroid.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.renteasyandroid.database.entity.RecentlyUpdatedEntity
import com.example.renteasyandroid.feature.main.data.model.RecentlyUpdatedResponse

@Dao
interface RecentlyUpdatedDao {

    // query function to add
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(entity: List<RecentlyUpdatedResponse>)
//
//    // query function to fetch
//    @Query("SELECT * FROM recently_updated")
//    fun getRecentlyUpdatedData(): List<RecentlyUpdatedResponse>
//
//    @Query("SELECT * FROM recently_updated where id=:id")
//    fun getRecentlyUpdatedDataById(id: Int): RecentlyUpdatedResponse
//
//    // query function to update
//    @Update
//    suspend fun update(entity: RecentlyUpdatedResponse)
//
//    // query function to delete
//    @Query("DELETE from recently_updated")
//    suspend fun delete()
}