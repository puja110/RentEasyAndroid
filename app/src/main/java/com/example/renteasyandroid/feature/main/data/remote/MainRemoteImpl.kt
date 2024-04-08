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

    override suspend fun getRecentlyUpdatedResponse(): List<RecentlyUpdatedResponse> {
        val items = mutableListOf<RecentlyUpdatedResponse>()
        items.add(
            RecentlyUpdatedResponse(
                id = 1,
                title = "Small nature friendly house",
                image = "https://images.pexels.com/photos/1396122/pexels-photo-1396122.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                address = "Owen street, Barrie",
                roomCount = "2",
                price = "2400",
                currency_code = "$",
                price_type = "month",
                status = "Available",
                rating = 3.5F,
                owner = "Olasile Oladipupo",
                description = "Charming and spacious house available for rent! This inviting home features [number of bedrooms/bathrooms], a [mention any standout features like a modern kitchen, scenic backyard, etc.], and is conveniently located near [mention nearby amenities or landmarks]. Perfect for anyone seeking comfort, convenience, and a place to call home."
            )
        )

        items.add(
            RecentlyUpdatedResponse(
                id = 2,
                title = "Apartment with great sea view",
                image = "https://images.pexels.com/photos/1732414/pexels-photo-1732414.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                address = "Owen street, Barrie",
                roomCount = "2",
                price = "5260",
                currency_code = "$",
                price_type = "month",
                status = "Booked",
                rating = 3.5F,
                owner = "Manushi Khadka",
                description = "Welcome to your new rental home! This cozy [number of bedrooms/bathrooms] house boasts [highlight any unique features like hardwood floors, a fireplace, or a backyard oasis]. Nestled in a peaceful neighborhood close to [mention nearby attractions or conveniences], this property offers comfort and tranquility for your ideal living experience"
            )
        )
        items.add(
            RecentlyUpdatedResponse(
                id = 3,
                title = "Countryside home",
                image = "https://images.pexels.com/photos/3990589/pexels-photo-3990589.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                address = "Owen street, Barrie",
                roomCount = "2",
                price = "5260",
                currency_code = "$",
                price_type = "month",
                status = "Booked",
                rating = 3.5F,
                owner = "Suyog Shrestha",
                description = "Stunning rental opportunity! This [number of bedrooms/bathrooms] house showcases [highlight any exceptional features such as a spacious living area, updated appliances, or a scenic view]. Located in a prime location near [mention nearby amenities or transportation hubs], this home promises convenience and elegance, perfect for creating unforgettable memories."
            )
        )
        items.add(
            RecentlyUpdatedResponse(
                id = 4,
                title = "Small nature friendly house",
                image = "https://images.pexels.com/photos/164558/pexels-photo-164558.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
                address = "Owen street, Barrie",
                roomCount = "2",
                price = "2400",
                currency_code = "$",
                price_type = "month",
                status = "Available",
                rating = 5.0f,
                owner = "Puja Shrestha",
                description = "Your perfect rental awaits! This [number of bedrooms/bathrooms] home exudes [mention any notable features such as modern design, ample natural light, or a landscaped garden]. Situated in a desirable neighborhood close to [highlight nearby attractions or facilities], this property offers comfort and style, making it an ideal place to call home."
            )
        )

        items.add(
            RecentlyUpdatedResponse(
                id = 5,
                title = "Beautiful House with a view",
                image = "https://images.pexels.com/photos/209274/pexels-photo-209274.jpeg?auto=compress&cs=tinysrgb&w=1200",
                address = "Owen street, Barrie",
                roomCount = "2",
                price = "1900",
                currency_code = "$",
                price_type = "month",
                status = "Booked",
                rating = 5.0f,
                owner = "Pramod Regmi",
                description = "Your perfect rental awaits! This [number of bedrooms/bathrooms] home exudes [mention any notable features such as modern design, ample natural light, or a landscaped garden]. Situated in a desirable neighborhood close to [highlight nearby attractions or facilities], this property offers comfort and style, making it an ideal place to call home."
            )
        )

        return items
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