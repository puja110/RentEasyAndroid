package com.example.renteasyandroid.feature.auth.data

interface AuthRepository {
    interface Local {
    }

    interface Remote {

    }

    suspend fun getData():String
}