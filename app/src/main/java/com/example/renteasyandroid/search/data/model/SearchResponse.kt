package com.example.renteasyandroid.search.data.model

data class SearchResponse(
    var id: String? = null,
    val description: String? = null,
    val imageUrls: List<String>? = null,
    val isBooked: Boolean? = null,
    val isNegotiable: Boolean? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val posterUserID: String? = null,
    val propertyAddress: String? = null,
    val propertyAmount: Int? = null,
    val propertyCategory: String? = null,
    val propertyName: String? = null,
    val propertySize: String? = null
)