package com.example.renteasyandroid.feature.main.data

import com.example.renteasyandroid.feature.main.data.model.AddPostRequest
import com.example.renteasyandroid.feature.main.data.model.CategoryResponse
import com.example.renteasyandroid.feature.main.data.model.FavouritesResponse
import com.example.renteasyandroid.feature.main.data.model.HomeFacilitiesResponse
import com.example.renteasyandroid.feature.main.data.model.NearPublicFacilitiesResponse
import com.example.renteasyandroid.feature.main.data.model.RecentlyUpdatedResponse
import com.example.renteasyandroid.feature.main.data.model.UserDetail

interface MainRepository {
    interface Local {
//        suspend fun insert(response: List<RecentlyUpdatedResponse>): Boolean
//        suspend fun getRecentlyUpdatedData(): List<RecentlyUpdatedResponse>
    }

    interface Remote {

        suspend fun getUserDetail(): UserDetail
        suspend fun getCategories(): List<CategoryResponse>
        suspend fun getRecentlyUpdatedResponse(): List<RecentlyUpdatedResponse>
        suspend fun getFavouritesResponse(): List<FavouritesResponse>
        suspend fun getHomeFacilitiesResponse(): List<HomeFacilitiesResponse>
        suspend fun getNearPublicFacilitiesResponse(): List<NearPublicFacilitiesResponse>
        suspend fun postRent(request: AddPostRequest): String
        suspend fun setFavorites(propertyId: String, remove: Boolean): Boolean
    }

    suspend fun getCategories(): List<CategoryResponse>
//    suspend fun saveRecentlyUpdatedResponse()
    suspend fun getRecentlyUpdatedResponse(): List<RecentlyUpdatedResponse>
    suspend fun getFavouritesResponse(): List<FavouritesResponse>
    suspend fun getHomeFacilitiesResponse(): List<HomeFacilitiesResponse>
    suspend fun getNearPublicFacilitiesResponse(): List<NearPublicFacilitiesResponse>
    suspend fun postRent(request: AddPostRequest): String
    suspend fun setFavorites(propertyId: String, remove: Boolean): Boolean
    suspend fun getUserDetail(): UserDetail
}