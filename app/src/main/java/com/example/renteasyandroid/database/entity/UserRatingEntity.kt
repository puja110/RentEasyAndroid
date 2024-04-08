package com.example.renteasyandroid.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_rating_table")
data class UserRatingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "property_id") val propertyId: Int?,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "rating") val rating: Float?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "user_image") val userImage: String?,
)