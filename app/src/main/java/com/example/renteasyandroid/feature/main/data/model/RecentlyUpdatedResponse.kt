package com.example.renteasyandroid.feature.main.data.model

data class RecentlyUpdatedResponse(
    var id: String,
    val description: String,
    val imageUrls: List<String>,
    val isBooked: Boolean,
    val isNegotiable: Boolean,
    val latitude: Double,
    val longitude: Double,
    val posterUserID: String,
    val propertyAddress: String,
    val propertyAmount: Int,
    val propertyCategory: String,
    val propertyName: String,
    val propertySize: String
)
