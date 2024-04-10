package com.example.renteasyandroid.feature.main.data.remote

import android.util.Log
import com.example.renteasyandroid.feature.main.data.MainRepository
import com.example.renteasyandroid.feature.main.data.model.AddPostRequest
import com.example.renteasyandroid.feature.main.data.model.CategoryResponse
import com.example.renteasyandroid.feature.main.data.model.FavouritesResponse
import com.example.renteasyandroid.feature.main.data.model.HomeFacilitiesResponse
import com.example.renteasyandroid.feature.main.data.model.NearPublicFacilitiesResponse
import com.example.renteasyandroid.feature.main.data.model.RecentlyUpdatedResponse
import com.example.renteasyandroid.feature.main.data.model.UserDetail
import com.example.renteasyandroid.feature.main.data.model.UserFavouriteResponse
import com.example.renteasyandroid.remote.FirebaseApiService
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
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

    /**
     * @description: Gets all the user's details of the users collection
     */
    override suspend fun getUserDetail(): UserDetail {
        currentUser?.let { user ->
            val snapshot = apiService.collection("users").document(user.uid).get().await()
            val userDetail = snapshot.toObject(UserDetail::class.java)
            return userDetail ?: throw Exception("User details not found.")
        } ?: throw Exception("No authenticated user found.")
    }

    /**
     * @description : Update users detail with all the parameters like
     * firstName, lastName, email, phoneNumber
     * and returns true if the network call is successful
     */
    override suspend fun updateUserDetail(user: UserDetail): Boolean {
        val currentUser = Firebase.auth.currentUser
        return try {
            // Fetch the current user document from Firestore
            val docRef = currentUser?.let { apiService.collection("users").document(it.uid) }
            val snapshot = docRef?.get()?.await()
            val existingUser = snapshot?.toObject<UserDetail>() ?: UserDetail()

            // Create a map to hold the updated user details
            val updatedUserMap = hashMapOf<String, Any>()

            // Check each field in inputUser for null and use existing values if null
            updatedUserMap["email"] = user.email ?: existingUser.email.toString()
            updatedUserMap["firstName"] = user.firstName ?: existingUser.firstName.toString()
            updatedUserMap["lastName"] = user.lastName ?: existingUser.lastName.toString()
            updatedUserMap["phoneNumber"] = user.phoneNumber ?: existingUser.phoneNumber.toString()
            docRef?.set(updatedUserMap)?.await()
            true
        } catch (e: Exception) {
            Log.w(TAG, "Error updating user details", e)
            false
        }
    }

    /**
     * Fetches a static category from a mutable list of <CategoryResponse>
     */
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

    /**
     * Recently Updated Response @description: fetch recently updated response from
     * favorites sub collection from users collection
     */
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
                    recentlyUpdated.id =
                        document.id // Assuming RecentlyUpdatedResponse has an 'id' field
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
            // Initialize an empty list to hold the favorite responses
            val items = mutableListOf<FavouritesResponse>()

            // Check if there is a currently authenticated user
            if (currentUser != null) {
                // Fetch the favorites subcollection for the current user from Firestore
                val snapshot = apiService.collection("users")
                    .document(currentUser.uid)
                    .collection("favorites")
                    .get()
                    .await()

                // Iterate through each document in the favorites subcollection
                snapshot.documents.forEach { document ->
                    // Convert the document to a UserFavouriteResponse object
                    document.toObject<UserFavouriteResponse>()?.let { favorites ->
                        // Fetch the corresponding property details for the favorite
                        val propertySnapshot = favorites.propertyId?.let {
                            apiService.collection("properties").document(it).get().await()
                        }
                        // Check if the property snapshot is not null
                        if (propertySnapshot != null) {
                            // Convert the property snapshot to a FavouritesResponse object
                            propertySnapshot.toObject<FavouritesResponse>()?.let {
                                // Assign the document ID as the response ID
                                it.id = document.id
                                // Add the response to the items list
                                items.add(it)
                            }
                        }
                    }
                }
            }

            // Return the list of favorite responses
            items
        } catch (e: Exception) {
            // Handle the exception, e.g., log it or return an empty list
            Log.e("Firestore Error", "Error fetching document", e)
            emptyList()
        }
    }


    /**
     * Fetch Home Facilities Response: fetches a static mutable list of <HomeFacilitiesResponse>
     */
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

    /**
     * Fetch Public Facilities Response: fetches a static mutable list of <NearPublicFacilitiesResponse>
     */
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


    /**
     * Post Rent Method @description : Posts the rental submission to Firestore database
     * Collection name: "properties"
     */
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

    /**
     * Implementation of setting favourites in the user's sub collection named "favourites" in the Firestore collection
     */
    override suspend fun setFavorites(propertyId: String, remove: Boolean): Boolean {
        val currentUser = Firebase.auth.currentUser
        return try {
            // Ensure we have a logged-in user
            if (currentUser != null) {
                // Reference to the "favorites" subcollection under the user's document
                val favoritesSubcollectionRef =
                    apiService.collection("users").document(currentUser.uid).collection("favorites")

                if (remove) {
                    // Query the subcollection for documents with the matching "propertyId"
                    val querySnapshot =
                        favoritesSubcollectionRef.whereEqualTo("propertyId", propertyId).get()
                            .await()

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