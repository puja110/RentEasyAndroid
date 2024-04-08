package com.example.renteasyandroid.search.data.remote

import com.example.renteasyandroid.remote.ApiService
import com.example.renteasyandroid.search.data.SearchRepository
import com.example.renteasyandroid.search.data.model.SearchResponse

class SearchRemoteImpl private constructor() : SearchRepository.Remote {

    private val apiService by lazy {
        ApiService.getInstance()
    }

    companion object {
        @Volatile
        private var instance: SearchRepository.Remote? = null

        @Synchronized
        fun getInstance(): SearchRepository.Remote {
            if (instance != null) {
                return instance!!
            }
            return SearchRemoteImpl().also { instance = it }
        }
    }

    override suspend fun getSearchResponse(): List<SearchResponse> {
        val items = mutableListOf<SearchResponse>()
        items.add(
            SearchResponse(
                id = "5",
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

        items.add(
            SearchResponse(
                id = "2",
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
            SearchResponse(
                id = "3",
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
            SearchResponse(
                id = "4",
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
            SearchResponse(
                id = "5",
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
            SearchResponse(
                id = "6",
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

}