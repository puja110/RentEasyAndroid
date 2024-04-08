package com.example.renteasyandroid.search.data.remote

import android.util.Log
import com.example.renteasyandroid.feature.main.data.model.RecentlyUpdatedResponse
import com.example.renteasyandroid.remote.ApiService
import com.example.renteasyandroid.search.data.SearchRepository
import com.example.renteasyandroid.search.data.model.SearchResponse
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class SearchRemoteImpl private constructor() : SearchRepository.Remote {

    private val apiService by lazy {
        ApiService.getInstance()
    }

    companion object {
        @Volatile
        private var instance: SearchRepository.Remote? = null

        @Synchronized
        fun getInstance(): SearchRepository.Remote {
            if (instance != null) {
                return instance!!
            }
            return SearchRemoteImpl().also { instance = it }
        }
    }

    override suspend fun getSearchResponse(): List<SearchResponse> {
        return try {
            val snapshot = apiService.collection("properties").get().await()
            val items = mutableListOf<SearchResponse>()
            for (document in snapshot.documents) {
                document.toObject<SearchResponse>()?.let { data ->
                    data.id = document.id
                    items.add(data)
                }
            }

            items
        } catch (e: Exception) {
            // Handle the exception, e.g., log it or return an empty list
            throw Error(e)
        }
    }

}