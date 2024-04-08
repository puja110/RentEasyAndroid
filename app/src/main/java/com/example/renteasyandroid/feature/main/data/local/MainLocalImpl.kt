package com.example.renteasyandroid.feature.main.data.local

import android.content.Context
import com.example.renteasyandroid.database.DatabaseManager
import com.example.renteasyandroid.database.entity.RecentlyUpdatedEntity
import com.example.renteasyandroid.feature.main.data.MainRepository
import com.example.renteasyandroid.feature.main.data.model.RecentlyUpdatedResponse

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
//    override suspend fun insert(response: List<RecentlyUpdatedResponse>): Boolean {
//        val items = mutableListOf<RecentlyUpdatedResponse>()
//        response.forEach {
//            items.add(
//                RecentlyUpdatedResponse(
//                    id = it.id,
//                    description = it.description,
//                    imageUrls = it.imageUrls,
//                    isBooked = it.isBooked,
//                    isNegotiable = it.isNegotiable,
//                    latitude = it.latitude,
//                    longitude = it.longitude,
//                    posterUserID = it.posterUserID,
//                    propertyAddress = it.propertyAddress,
//                    propertyAmount = it.propertyAmount,
//                    propertyCategory = it.propertyCategory,
//                    propertyName = it.propertyName,
//                    propertySize = it.propertySize
//                )
//            )
//        }
//        databaseManager.getInstance().getRecentlyUpdatedDao().delete()
//        databaseManager.getInstance().getRecentlyUpdatedDao().insert(items)
//        return true
//    }

    //fetch the list of recently updated data from the local database
//    override suspend fun getRecentlyUpdatedData(): List<RecentlyUpdatedResponse> {
//        val entity = databaseManager.getInstance().getRecentlyUpdatedDao().getRecentlyUpdatedData()
//        return entity.map {
//            RecentlyUpdatedResponse(
//                id = it.id,
//                description = it.description,
//                imageUrls = it.imageUrls,
//                isBooked = it.isBooked,
//                isNegotiable = it.isNegotiable,
//                latitude = it.latitude,
//                longitude = it.longitude,
//                posterUserID = it.posterUserID,
//                propertyAddress = it.propertyAddress,
//                propertyAmount = it.propertyAmount,
//                propertyCategory = it.propertyCategory,
//                propertyName = it.propertyName,
//                propertySize = it.propertySize
//            )
//        }
//    }
}
