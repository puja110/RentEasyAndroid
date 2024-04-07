package com.example.renteasyandroid.feature.main.data.local

import android.content.Context
import com.example.renteasyandroid.database.DatabaseManager
import com.example.renteasyandroid.database.entity.RecentlyUpdatedEntity
import com.example.renteasyandroid.feature.main.data.MainRepository
import com.example.renteasyandroid.feature.main.data.model.RecentlyUpdatedResponse
import com.example.renteasyandroid.search.data.local.SearchLocalImpl

class MainLocalImpl private constructor(
    private val databaseManager: DatabaseManager,
) : MainRepository.Local {

    companion object {
        @Volatile
        private var instance: MainRepository.Local? = null

        @Synchronized
        fun getInstance(context: Context): MainRepository.Local {
            if (instance != null) {
                return instance!!
            }
            val database = DatabaseManager(context)
            return MainLocalImpl(database).also { instance = it }
        }
    }

    //inserts the recently updated data to the local database
    override suspend fun insert(response: List<RecentlyUpdatedResponse>): Boolean {
        val items = mutableListOf<RecentlyUpdatedEntity>()
        response.forEach {
            items.add(
                RecentlyUpdatedEntity(
                    title = it.title,
                    image = it.image,
                    address = it.address,
                    roomCount = it.roomCount,
                    currencyCode = it.currency_code,
                    price = it.price,
                    priceType = it.price_type,
                    status = it.status,
                    rating = it.rating,
                    owner = it.owner,
                    description = it.description
                )
            )
        }
        databaseManager.getInstance().getRecentlyUpdatedDao().delete()
        databaseManager.getInstance().getRecentlyUpdatedDao().insert(items)
        return true
    }

    //fetch the list of recently updated data from the local database
    override suspend fun getRecentlyUpdatedData(): List<RecentlyUpdatedResponse> {
        val entity = databaseManager.getInstance().getRecentlyUpdatedDao().getRecentlyUpdatedData()
        return entity.map {
            RecentlyUpdatedResponse(
                id = it.id ?: -1,
                title = it.title ?: "",
                image = it.image ?: "",
                address = it.address ?: "",
                roomCount = it.roomCount ?: "",
                currency_code = it.currencyCode ?: "",
                price = it.price ?: "",
                price_type = it.priceType ?: "",
                status = it.status ?: "",
                rating = it.rating ?: 5F,
                owner = it.owner ?: "",
                description = it.description ?: ""
            )
        }
    }
}
