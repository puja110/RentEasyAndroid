package com.example.renteasyandroid.utils

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class ErrorInterceptor constructor(
    val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!checkNetworkAvailability(context)) {
            throw NetworkNotAvailableException("No Internet Connection.")
        }
        return try {
            chain.proceed(chain.request())
        } catch (e: Exception) {
            throw e
        }
    }
}