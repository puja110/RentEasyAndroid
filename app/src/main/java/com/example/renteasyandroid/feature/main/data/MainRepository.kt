package com.example.renteasyandroid.feature.main.data

interface MainRepository {
    interface Local {
    }

    interface Remote {

    }

    suspend fun getData():String
}