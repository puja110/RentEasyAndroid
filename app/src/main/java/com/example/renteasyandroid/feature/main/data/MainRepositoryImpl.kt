package com.example.renteasyandroid.feature.main.data

import com.example.renteasyandroid.feature.main.data.local.MainLocalImpl
import com.example.renteasyandroid.feature.main.data.model.CategoryResponse
import com.example.renteasyandroid.feature.main.data.model.FavouritesResponse
import com.example.renteasyandroid.feature.main.data.model.HomeFacilitiesResponse
import com.example.renteasyandroid.feature.main.data.model.NearPublicFacilitiesResponse
import com.example.renteasyandroid.feature.main.data.model.RecentlyUpdatedResponse
import com.example.renteasyandroid.feature.main.data.remote.MainRemoteImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepositoryImpl constructor(
    private val localRepository: MainRepository.Local,
    private val remoteRepository: MainRepository.Remote
) : MainRepository {

    companion object {
        @Volatile
        private var instance: MainRepository? = null

        @Synchronized
        fun getInstance(): MainRepository {
            if (instance != null) {
                return instance!!
            }

            val local = MainLocalImpl.getInstance()
            val remote = MainRemoteImpl.getInstance()
            return MainRepositoryImpl(local, remote).also { instance = it }
        }
    }

    override suspend fun getCategories(): List<CategoryResponse> {
        return withContext(Dispatchers.IO) {
            remoteRepository.getCategories()
        }
    }

    override suspend fun getRecentlyUpdatedResponse(): List<RecentlyUpdatedResponse> {
        return withContext(Dispatchers.IO) {
            remoteRepository.getRecentlyUpdatedResponse()
        }
    }

    override suspend fun getFavouritesResponse(): List<FavouritesResponse> {
        return withContext(Dispatchers.IO) {
            remoteRepository.getFavouritesResponse()
        }
    }

    override suspend fun getHomeFacilitiesResponse(): List<HomeFacilitiesResponse> {
        return withContext(Dispatchers.IO) {
            remoteRepository.getHomeFacilitiesResponse()
        }
    }

    override suspend fun getNearPublicFacilitiesResponse(): List<NearPublicFacilitiesResponse> {
        return withContext(Dispatchers.IO) {
            remoteRepository.getNearPublicFacilitiesResponse()
        }
    }
}