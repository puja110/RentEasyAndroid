package com.example.renteasyandroid.remote

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST
    fun postSampleApi(@Body params: Map<String, Any>): String

    companion object {
        @Volatile
        private var apiService: ApiService? = null

        @Synchronized
        fun getInstance(): ApiService {
            if (apiService != null) {
                return apiService!!
            }
            val okHttpClient = OkHttpClient.getInstance()

            apiService = Retrofit.Builder()
                .baseUrl("")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build().create(ApiService::class.java)

            return apiService!!
        }
    }
}