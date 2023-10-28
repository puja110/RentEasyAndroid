package com.example.renteasyandroid.feature.main.data.remote

import com.example.renteasyandroid.feature.main.data.MainRepository
import com.example.renteasyandroid.feature.main.data.model.CategoryResponse
import com.example.renteasyandroid.feature.main.data.model.FavouritesResponse
import com.example.renteasyandroid.feature.main.data.model.RecentlyUpdatedResponse
import com.example.renteasyandroid.remote.ApiService

class MainRemoteImpl private constructor() : MainRepository.Remote {

    private val apiService by lazy {
        ApiService.getInstance()
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
                image = "https://fastly.picsum.photos/id/20/3670/2462.jpg?hmac=CmQ0ln-k5ZqkdtLvVO23LjVAEabZQx2wOaT4pyeG10I",
                searchCount = "64"
            )
        )
        items.add(
            CategoryResponse(
                id = 2,
                title = "Condos",
                image = "https://fastly.picsum.photos/id/19/2500/1667.jpg?hmac=7epGozH4QjToGaBf_xb2HbFTXoV5o8n_cYzB7I4lt6g",
                searchCount = "34"
            )
        )
        items.add(
            CategoryResponse(
                id = 3,
                title = "Flat",
                image = "https://fastly.picsum.photos/id/16/2500/1667.jpg?hmac=uAkZwYc5phCRNFTrV_prJ_0rP0EdwJaZ4ctje2bY7aE",
                searchCount = "204"
            )
        )
        items.add(
            CategoryResponse(
                id = 4,
                title = "Apartment",
                image = "https://fastly.picsum.photos/id/0/5000/3333.jpg?hmac=_j6ghY5fCfSD6tvtcV74zXivkJSPIfR9B8w34XeQmvU",
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
                image = "https://fastly.picsum.photos/id/28/4928/3264.jpg?hmac=GnYF-RnBUg44PFfU5pcw_Qs0ReOyStdnZ8MtQWJqTfA",
                address = "Owen street, Barrie",
                roomCount = "2",
                price = "2400",
                currency_code = "$",
                price_type = "month",
                status = "Available"
            )
        )

        items.add(
            RecentlyUpdatedResponse(
                id = 2,
                title = "Apartment with great sea view",
                image = "https://fastly.picsum.photos/id/29/4000/2670.jpg?hmac=rCbRAl24FzrSzwlR5tL-Aqzyu5tX_PA95VJtnUXegGU",
                address = "Owen street, Barrie",
                roomCount = "2",
                price = "5260",
                currency_code = "$",
                price_type = "month",
                status = "Booked"
            )
        )
        items.add(
            RecentlyUpdatedResponse(
                id = 2,
                title = "Countryside home",
                image = "https://fastly.picsum.photos/id/24/4855/1803.jpg?hmac=ICVhP1pUXDLXaTkgwDJinSUS59UWalMxf4SOIWb9Ui4",
                address = "Owen street, Barrie",
                roomCount = "2",
                price = "5260",
                currency_code = "$",
                price_type = "month",
                status = "Booked"
            )
        )
        items.add(
            RecentlyUpdatedResponse(
                id = 1,
                title = "Small nature friendly house",
                image = "https://fastly.picsum.photos/id/27/3264/1836.jpg?hmac=p3BVIgKKQpHhfGRRCbsi2MCAzw8mWBCayBsKxxtWO8g",
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

    override suspend fun getFavouritesResponse(): List<FavouritesResponse> {
        val items = mutableListOf<FavouritesResponse>()
        items.add(
            FavouritesResponse(
                id = 1,
                title = "Small nature friendly house",
                image = "https://fastly.picsum.photos/id/28/4928/3264.jpg?hmac=GnYF-RnBUg44PFfU5pcw_Qs0ReOyStdnZ8MtQWJqTfA",
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
                image = "https://fastly.picsum.photos/id/29/4000/2670.jpg?hmac=rCbRAl24FzrSzwlR5tL-Aqzyu5tX_PA95VJtnUXegGU",
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
                id = 2,
                title = "Countryside home",
                image = "https://fastly.picsum.photos/id/24/4855/1803.jpg?hmac=ICVhP1pUXDLXaTkgwDJinSUS59UWalMxf4SOIWb9Ui4",
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
                id = 1,
                title = "Small nature friendly house",
                image = "https://fastly.picsum.photos/id/27/3264/1836.jpg?hmac=p3BVIgKKQpHhfGRRCbsi2MCAzw8mWBCayBsKxxtWO8g",
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
}