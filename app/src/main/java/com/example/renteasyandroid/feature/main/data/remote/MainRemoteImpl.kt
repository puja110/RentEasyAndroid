package com.example.renteasyandroid.feature.main.data.remote

import com.example.renteasyandroid.feature.main.data.MainRepository
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
}