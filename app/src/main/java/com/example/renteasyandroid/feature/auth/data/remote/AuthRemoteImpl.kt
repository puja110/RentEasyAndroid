package com.example.renteasyandroid.feature.auth.data.remote

import com.example.renteasyandroid.feature.auth.data.AuthRepository
import com.example.renteasyandroid.remote.ApiService

class AuthRemoteImpl private constructor() : AuthRepository.Remote {

    private val apiService by lazy {
        ApiService.getInstance()
    }

    companion object {
        @Volatile
        private var instance: AuthRepository.Remote? = null

        @Synchronized
        fun getInstance(): AuthRepository.Remote {
            if (instance != null) {
                return instance!!
            }
            return AuthRemoteImpl().also { instance = it }
        }
    }
}