package com.example.renteasyandroid.feature.main.data.remote

import android.util.Log
import com.example.renteasyandroid.feature.main.data.MainRepository
import com.example.renteasyandroid.feature.main.data.model.CategoryResponse
import com.example.renteasyandroid.feature.main.data.model.FavouritesResponse
import com.example.renteasyandroid.feature.main.data.model.HomeFacilitiesResponse
import com.example.renteasyandroid.feature.main.data.model.NearPublicFacilitiesResponse
import com.example.renteasyandroid.feature.main.data.model.RecentlyUpdatedResponse
import com.example.renteasyandroid.feature.main.data.model.UserFavouriteResponse
import com.example.renteasyandroid.remote.ApiService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class MainRemoteImpl private constructor() : MainRepository.Remote {

    private val apiService by lazy {
        ApiService.getInstance()
    }

    private val currentUser = Firebase.auth.currentUser

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

        private const val TAG = "MainRemoteImpl"
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

    override suspend fun getRecentlyUpdatedResponse(): List<RecentlyUpdatedResponse> {
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
        return try {

            val items = mutableListOf<FavouritesResponse>()
            if (currentUser != null) {
                val snapshot = apiService.collection("users").document(currentUser.uid).collection("favorites").get().await()
                snapshot.documents.forEach { document ->
                    document.toObject<UserFavouriteResponse>()?.let { favorites ->
                        val item = apiService.collection("properties").document(favorites.propertyId).get().await()
                        item.toObject<FavouritesResponse>()?.let {
                            items.add(it)
                        }
                    }
                }
            }
            items
        } catch (e: Exception) {
            // Handle the exception, e.g., log it or return an empty list
            Log.e("Firestore Error", "Error fetching document", e)
            emptyList()
        }
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

    override suspend fun setFavorites(propertyId: String): Boolean {
        val currentUser = Firebase.auth.currentUser
        return try {
            // Ensure we have a logged-in user
            if (currentUser != null) {
                // Reference to the user's document
                val userDocRef = apiService.collection("users").document(currentUser.uid)

                // Atomically add the propertyId to the "favorites" array field
                userDocRef.update("favorites", FieldValue.arrayUnion(propertyId)).await()
                Log.d(Companion.TAG, "Favorites updated successfully")
                true
            } else {
                Log.w(Companion.TAG, "No authenticated user found")
                false
            }
        } catch (e: Exception) {
            Log.w(Companion.TAG, "Error updating favorites", e)
            false
        }
    }

}