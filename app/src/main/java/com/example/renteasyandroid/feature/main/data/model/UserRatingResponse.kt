package com.example.renteasyandroid.feature.main.data.model

data class UserRatingResponse(
    var propertyId: Int,
    var username: String,
    var userImage: String,
    var review: String,
    var rating: Double
)

object UserRatingResponseList {

    fun getModel(): List<UserRatingResponse> {

        val userReview1 = UserRatingResponse(
            1,
            "Puja Shrestha",
            "https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
            review = "The place is what I expected. Thank you!",
            rating = 4.0
        )

        val userReview2 = UserRatingResponse(
            2,
            "John Doe",
            "https://images.pexels.com/photos/34534/people-peoples-homeless-male.jpg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2",
            review = "Amazing!",
            rating = 4.5
        )

        val itemList: ArrayList<UserRatingResponse> = ArrayList()
        itemList.add(userReview1)
        itemList.add(userReview2)
        return itemList
    }
}