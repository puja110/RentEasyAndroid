package com.example.renteasyandroid.search.data.model

data class SearchResponse(
    var id: Int,
    var title: String,
    var image: String,
    var address: String,
    var roomCount: String,
    var currency_code: String,
    var price: String,
    var price_type: String,
    var status: String,
    var rating: Float,
    var owner: String,
    var description: String,
)