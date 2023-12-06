package com.example.renteasyandroid.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recently_updated")
data class RecentlyUpdatedEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "address") val address: String?,
    @ColumnInfo(name = "roomCount") val roomCount: String?,
    @ColumnInfo(name = "currency_code") val currencyCode: String?,
    @ColumnInfo(name = "price") val price: String?,
    @ColumnInfo(name = "price_type") val priceType: String?,
    @ColumnInfo(name = "status") val status: String?,
    @ColumnInfo(name = "rating") val rating: Float?,
    @ColumnInfo(name = "owner") val owner: String?,
    @ColumnInfo(name = "description") val description: String?,
)