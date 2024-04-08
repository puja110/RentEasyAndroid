package com.example.renteasyandroid.feature.auth.data

import com.example.renteasyandroid.database.entity.UserEntity

interface AuthRepository {
    interface Local {

    }

    interface Remote {
        suspend fun register(email: String, password: String): Boolean
        suspend fun authenticateUser(email: String, password: String): String
    }

    suspend fun signup(email: String, password: String): Boolean

    suspend fun authenticateUser(email: String, password: String): String


}