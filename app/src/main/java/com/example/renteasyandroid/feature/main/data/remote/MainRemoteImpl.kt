package com.example.renteasyandroid.feature.main.data.remote

import android.util.Log
import com.example.renteasyandroid.feature.main.data.MainRepository
import com.example.renteasyandroid.feature.main.data.model.AddPostRequest
import com.example.renteasyandroid.feature.main.data.model.CategoryResponse
import com.example.renteasyandroid.feature.main.data.model.FavouritesResponse
import com.example.renteasyandroid.feature.main.data.model.HomeFacilitiesResponse
import com.example.renteasyandroid.feature.main.data.model.NearPublicFacilitiesResponse
import com.example.renteasyandroid.feature.main.data.model.RecentlyUpdatedResponse
import com.example.renteasyandroid.remote.FirebaseApiService
import com.example.renteasyandroid.remote.ApiService
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainRemoteImpl private constructor() : MainRepository.Remote {

    private val apiService by lazy {
        FirebaseApiService.getInstance()
    }

    companion object {
        @Volatile
        private var instance: MainRepository.Remote? = null

        @Synchronized
        fun getInstance(): MainRepository.Remote {
            if (instance != null) {
                return instance!!
            }
            return MainRemoteImpl().also { instance = it }
        }
    }

    override suspend fun getCategories(): List<CategoryResponse> {
        val items = mutableListOf<CategoryResponse>()
        items.add(
            CategoryResponse(
                id = 1,
                title = "Houses",
                image = "https://images.pexels.com/photos/164522/pexels-photo-164522.jpeg?auto=compress&cs=tinysrgb&w=800",
                searchCount = "64"
            )
        )
        items.add(
            CategoryResponse(
                id = 2,
                title = "Condos",
                image = "https://images.pexels.com/photos/53610/large-home-residential-house-architecture-53610.jpeg?auto=compress&cs=tinysrgb&w=1200",
                searchCount = "34"
            )
        )
        items.add(
            CategoryResponse(
                id = 3,
                title = "Flat",
                image = "https://images.pexels.com/photos/259593/pexels-photo-259593.jpeg?auto=compress&cs=tinysrgb&w=1200",
                searchCount = "204"
            )
        )
        items.add(
            CategoryResponse(
                id = 4,
                title = "Apartment",
                image = "https://images.pexels.com/photos/221540/pexels-photo-221540.jpeg?auto=compress&cs=tinysrgb&w=800",
                searchCount = "292"
            )
        )

        return items
    }

    override suspend fun getRecentlyUpdatedResponse(): List<RecentlyUpdatedResponse>  {
        return try {
            val snapshot = apiService.collection("properties").get().await()
            val items = mutableListOf<RecentlyUpdatedResponse>()
            for (document in snapshot.documents) {
                document.toObject<RecentlyUpdatedResponse>()?.let { recentlyUpdated ->
                    recentlyUpdated.id = document.id
                    Log.d("recently updated", recentlyUpdated.toString())
                    items.add(recentlyUpdated)
                }
            }

            items
        } catch (e: Exception) {
            // Handle the exception, e.g., log it or return an empty list
            throw Error(e)
        }
    }

    override suspend fun getFavouritesResponse(): List<FavouritesResponse> {
        val items = mutableListOf<FavouritesResponse>()
        items.add(
            FavouritesResponse(
                id = 1,
                title = "Small nature friendly house",
                image = "https://images.pexels.com/photos/277667/pexels-photo-277667.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                address = "Owen street, Barrie",
                roomCount = "2",
                price = "2400",
                currency_code = "$",
                price_type = "month",
                status = "Available"
            )
        )

        items.add(
            FavouritesResponse(
                id = 2,
                title = "Apartment with great sea view",
                image = "https://images.pexels.com/photos/7475561/pexels-photo-7475561.jpeg?auto=compress&cs=tinysrgb&w=1200&lazy=load",
                address = "Owen street, Barrie",
                roomCount = "2",
                price = "5260",
                currency_code = "$",
                price_type = "month",
                status = "Booked"
            )
        )
        items.add(
            FavouritesResponse(
                id = 3,
                title = "Countryside home",
                image = "https://images.pexels.com/photos/3935328/pexels-photo-3935328.jpeg?auto=compress&cs=tinysrgb&w=1200&lazy=load",
                address = "Owen street, Barrie",
                roomCount = "2",
                price = "5260",
                currency_code = "$",
                price_type = "month",
                status = "Booked"
            )
        )
        items.add(
            FavouritesResponse(
                id = 4,
                title = "Small nature friendly house",
                image = "https://images.pexels.com/photos/10553915/pexels-photo-10553915.jpeg?auto=compress&cs=tinysrgb&w=1200&lazy=load",
                address = "Owen street, Barrie",
                roomCount = "2",
                price = "2400",
                currency_code = "$",
                price_type = "month",
                status = "Available"
            )
        )

        return items
    }

    override suspend fun getHomeFacilitiesResponse(): List<HomeFacilitiesResponse> {
        val items = mutableListOf<HomeFacilitiesResponse>()
        items.add(
            HomeFacilitiesResponse(
                id = 1,
                title = "Heating",
            )
        )
        items.add(
            HomeFacilitiesResponse(
                id = 2,
                title = "Laundry",
            )
        )
        items.add(
            HomeFacilitiesResponse(
                id = 3,
                title = "Free Parking",
            )
        )
        items.add(
            HomeFacilitiesResponse(
                id = 4,
                title = "Free WiFi",
            )
        )
        return items
    }

    override suspend fun getNearPublicFacilitiesResponse(): List<NearPublicFacilitiesResponse> {
        val items = mutableListOf<NearPublicFacilitiesResponse>()
        items.add(
            NearPublicFacilitiesResponse(
                id = 1,
                title = "Minimarket",
                distance = "200m"
            )
        )
        items.add(
            NearPublicFacilitiesResponse(
                id = 2,
                title = "Hospital",
                distance = "130m"

            )
        )
        items.add(
            NearPublicFacilitiesResponse(
                id = 3,
                title = "Public canteen",
                distance = "400m"
            )
        )
        items.add(
            NearPublicFacilitiesResponse(
                id = 4,
                title = "Train Station",
                distance = "500m"

            )
        )
        return items
    }

    override suspend fun postRent(
        request: AddPostRequest
    ): String {
        println("getUUidFirebase${request.posterUserID}")
        val data = hashMapOf(
            "description" to request.description,
            "isBooked" to request.isBooked,
            "isNegotiable" to request.isNegotiable,
            "latitude" to request.latitude,
            "longitude" to request.longitude,
            "posterUserID" to request.posterUserID,
            "propertyAddress" to request.propertyAddress,
            "propertyAmount" to request.propertyAmount,
            "propertyCategory" to request.propertyCategory,
            "propertyName" to request.propertyName,
            "propertySize" to request.propertySize,
            "imageUrls" to request.imageUrls
        )
        val response =
            apiService.collection("properties").add(data).addOnSuccessListener {
                Log.d("GETDOCUMENTID", "DocumentSnapshot added with ID: ${it.id}")
            }.addOnFailureListener { e ->
                Log.w("GETDOCUMENTID", "Error adding document", e)
            }
        return if (response.isSuccessful) {
            "Success"
        } else {
            response.exception?.message.toString()
        }
    }

}