package com.example.renteasyandroid.remote

import okhttp3.logging.HttpLoggingInterceptor

class LoggingInterceptor private constructor() {

    companion object {
        @Volatile
        private var loggingInterceptor: HttpLoggingInterceptor? = null

        @Synchronized
        fun getInstance(): HttpLoggingInterceptor {
            if (loggingInterceptor != null) {
                return loggingInterceptor!!
            }
            loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor!!.level = HttpLoggingInterceptor.Level.BODY
            return loggingInterceptor!!
        }
    }
}