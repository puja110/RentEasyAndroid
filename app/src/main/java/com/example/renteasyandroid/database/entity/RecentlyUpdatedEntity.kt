package com.example.renteasyandroid.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recently_updated")
data class RecentlyUpdatedEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "imageUrls") val imageUrls: String,
    @ColumnInfo(name = "isBooked") val isBooked: Boolean,
    @ColumnInfo(name = "isNegotiable") val isNegotiable: Boolean,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "posterUserID") val posterUserID: String,
    @ColumnInfo(name = "propertyAddress") val propertyAddress: String,
    @ColumnInfo(name = "propertyAmount") val propertyAmount: Int,
    @ColumnInfo(name = "propertyCategory") val propertyCategory: String,
    @ColumnInfo(name = "propertyName") val propertyName: String,
    @ColumnInfo(name = "propertySize") val propertySize: String

)