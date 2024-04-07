package com.example.renteasyandroid.search.data

import com.example.renteasyandroid.search.data.model.SearchResponse

interface SearchRepository {
    interface Local {
    }

    interface Remote {
        suspend fun getSearchResponse(): List<SearchResponse>
    }

    suspend fun getSearchResponse(): List<SearchResponse>
}