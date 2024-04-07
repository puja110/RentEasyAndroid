package com.example.renteasyandroid.search.data.local

import android.content.Context
import com.example.renteasyandroid.database.DatabaseManager
import com.example.renteasyandroid.search.data.SearchRepository

class SearchLocalImpl private constructor(
    private val databaseManager: DatabaseManager,
) : SearchRepository.Local {

    companion object {
        @Volatile
        private var instance: SearchRepository.Local? = null

        @Synchronized
        fun getInstance(context: Context): SearchRepository.Local {
            if (instance != null) {
                return instance!!
            }
            val database = DatabaseManager(context)
            return SearchLocalImpl(database).also { instance = it }
        }
    }
}
