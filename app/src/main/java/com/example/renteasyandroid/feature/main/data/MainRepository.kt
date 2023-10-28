package com.example.renteasyandroid.feature.main.data

import com.example.renteasyandroid.feature.main.data.model.CategoryResponse
import com.example.renteasyandroid.feature.main.data.model.FavouritesResponse
import com.example.renteasyandroid.feature.main.data.model.RecentlyUpdatedResponse

interface MainRepository {
    interface Local {
    }

    interface Remote {
        suspend fun getCategories(): List<CategoryResponse>
        suspend fun getRecentlyUpdatedResponse(): List<RecentlyUpdatedResponse>
        suspend fun getFavouritesResponse(): List<FavouritesResponse>
    }

    suspend fun getCategories(): List<CategoryResponse>
    suspend fun getRecentlyUpdatedResponse(): List<RecentlyUpdatedResponse>
    suspend fun getFavouritesResponse(): List<FavouritesResponse>
}