package com.example.renteasyandroid.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.renteasyandroid.database.entity.UserRatingEntity

@Dao
interface UserRatingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserRating(movie: UserRatingEntity)

    @Query("SELECT * FROM user_rating_table where property_id=:detailId")
    fun getUserRatingsByDetailId(detailId: Int): LiveData<List<UserRatingEntity>>

    @Query("SELECT COUNT (*) FROM user_rating_table where property_id=:detailId")
    suspend fun getSizeOfDatabaseById(detailId: Int): Int
}