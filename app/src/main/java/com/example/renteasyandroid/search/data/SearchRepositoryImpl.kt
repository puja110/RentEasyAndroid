package com.example.renteasyandroid.search.data

import android.content.Context
import com.example.renteasyandroid.search.data.local.SearchLocalImpl
import com.example.renteasyandroid.search.data.model.SearchResponse
import com.example.renteasyandroid.search.data.remote.SearchRemoteImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SearchRepositoryImpl constructor(
    private val localRepository: SearchRepository.Local,
    private val remoteRepository: SearchRepository.Remote
) : SearchRepository {

    companion object {
        @Volatile
        private var instance: SearchRepository? = null

        @Synchronized
        fun getInstance(context: Context): SearchRepository {
            if (instance != null) {
                return instance!!
            }

            val local = SearchLocalImpl.getInstance(context)
            val remote = SearchRemoteImpl.getInstance()
            return SearchRepositoryImpl(local, remote).also { instance = it }
        }
    }


    override suspend fun getSearchResponse(): List<SearchResponse> {
        return withContext(Dispatchers.IO) {
            remoteRepository.getSearchResponse()
        }
    }
}