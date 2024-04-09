package com.example.renteasyandroid.feature.main.data.remote

import android.util.Log
import com.example.renteasyandroid.feature.main.data.MainRepository
import com.example.renteasyandroid.feature.main.data.model.AddPostRequest
import com.example.renteasyandroid.feature.main.data.model.CategoryResponse
import com.example.renteasyandroid.feature.main.data.model.FavouritesResponse
import com.example.renteasyandroid.feature.main.data.model.HomeFacilitiesResponse
import com.example.renteasyandroid.feature.main.data.model.NearPublicFacilitiesResponse
import com.example.renteasyandroid.feature.main.data.model.RecentlyUpdatedResponse
import com.example.renteasyandroid.feature.main.data.model.UserFavouriteResponse
import com.example.renteasyandroid.remote.FirebaseApiService
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.tasks.await

class MainRemoteImpl private constructor() : MainRepository.Remote {

    private val apiService by lazy {
        FirebaseApiService.getInstance()
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
        val currentUser = Firebase.auth.currentUser
        return try {
            if (currentUser == null) {
                Log.w("Error", "No authenticated user found")
                return emptyList()
            }

            // Fetch the list of favourite property IDs once
            val favouritesSnapshot = apiService.collection("users")
                .document(currentUser.uid)
                .collection("favorites")
                .get().await()

            val favouriteIds = favouritesSnapshot.documents.map { it.id }


            val snapshot = apiService.collection("properties").get().await()
            val items = mutableListOf<RecentlyUpdatedResponse>()
            for (document in snapshot.documents) {
                document.toObject<RecentlyUpdatedResponse>()?.let { recentlyUpdated ->
                    // Check if the current property's ID is in the list of favourite IDs
                    recentlyUpdated.isFavourite = favouriteIds.contains(document.id)
                    recentlyUpdated.id = document.id // Assuming RecentlyUpdatedResponse has an 'id' field
                    Log.d("recently updated", recentlyUpdated.toString())
                    items.add(recentlyUpdated)
                }
            }

            items
        } catch (e: Exception) {
            Log.e("Error", "Error fetching recently updated properties", e)
            emptyList() // Return an empty list in case of error
        }
    }

    override suspend fun getFavouritesResponse(): List<FavouritesResponse> {
        return try {

            val items = mutableListOf<FavouritesResponse>()
            if (currentUser != null) {
                val snapshot = apiService.collection("users").document(currentUser.uid).collection("favorites").get().await()
                snapshot.documents.forEach { document ->
                    document.toObject<UserFavouriteResponse>()?.let { favorites ->
                        val propertySnapshot = favorites.propertyId?.let {
                            apiService.collection("properties").document(
                                it
                            ).get().await()
                        }
                        if (propertySnapshot != null) {
                            propertySnapshot.toObject<FavouritesResponse>()?.let { it
                                it.id = document.id
                                items.add(it)
                            }
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

    override suspend fun setFavorites(propertyId: String, remove: Boolean): Boolean {
        val currentUser = Firebase.auth.currentUser
        return try {
            // Ensure we have a logged-in user
            if (currentUser != null) {
                // Reference to the "favorites" subcollection under the user's document
                val favoritesSubcollectionRef = apiService.collection("users").document(currentUser.uid).collection("favorites")

                if (remove) {
                    // Query the subcollection for documents with the matching "propertyId"
                    val querySnapshot = favoritesSubcollectionRef.whereEqualTo("propertyId", propertyId).get().await()

                    // Delete all documents that match the query (should be only one if "propertyId" is unique)
                    querySnapshot.documents.forEach { document ->
                        document.reference.delete().await()
                    }
                    true
                } else {
                    // Create or overwrite a document with the given propertyId in the "favorites" subcollection
                    // Here, we're directly using the propertyId as the document ID for simplicity
                    // The favoriteData map can contain additional information if needed
                    val favoriteData = hashMapOf("propertyId" to propertyId)
                    favoritesSubcollectionRef.document(propertyId).set(favoriteData).await()
                    Log.d(TAG, "Favorite added successfully: $propertyId")
                    true
                }
            } else {
                Log.w(TAG, "No authenticated user found")
                false
            }
        } catch (e: Exception) {
            Log.w(TAG, "Error processing favorite", e)
            false
        }
    }

}