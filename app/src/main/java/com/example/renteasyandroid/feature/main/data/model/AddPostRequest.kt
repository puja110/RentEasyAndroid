package com.example.renteasyandroid.feature.main.data.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class AddPostRequest(
    val description: String? = null,
    val isBooked: Boolean? = false,
    val isNegotiable: Boolean? = true,
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
    val posterUserID: String? = null,
    val propertyAddress: String? = null,
    val propertyAmount: Long? = 0,
    val propertyCategory: String? = null,
    val propertyName: String? = null,
    val propertySize: String? = null,
    val imageUrls: List<String>? = emptyList()
)