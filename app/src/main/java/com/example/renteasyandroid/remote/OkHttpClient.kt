package com.example.renteasyandroid.remote

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkHttpClient private constructor() {
    companion object {
        @Volatile
        private var client: OkHttpClient? = null

        @Synchronized
        fun getInstance(): OkHttpClient {
            if (client != null) {
                return client!!
            }
            val loggingInterceptor = LoggingInterceptor.getInstance()
            client = OkHttpClient.Builder()
                .apply {
                    addInterceptor(loggingInterceptor)
                    connectTimeout(60, TimeUnit.SECONDS)
                    readTimeout(60, TimeUnit.SECONDS)
                }.build()

            return client!!
        }
    }
}