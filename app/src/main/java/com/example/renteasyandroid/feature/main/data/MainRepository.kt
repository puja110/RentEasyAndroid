package com.example.renteasyandroid.feature.main.data

import com.example.renteasyandroid.feature.main.data.model.CategoryResponse

interface MainRepository {
    interface Local {
    }

    interface Remote {
        suspend fun getCategories(): List<CategoryResponse>
    }

    suspend fun getCategories(): List<CategoryResponse>
}