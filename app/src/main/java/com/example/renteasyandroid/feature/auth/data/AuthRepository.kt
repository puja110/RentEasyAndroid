package com.example.renteasyandroid.feature.auth.data

import com.example.renteasyandroid.database.entity.UserEntity

interface AuthRepository {
    interface Local {
        suspend fun register(email: String, password: String): Boolean
        suspend fun authenticateUser(email: String, password: String): String
    }

    interface Remote {
        suspend fun registerUser(email: String, password: String): Boolean
        suspend fun authenticateUser(email: String, password: String): String
    }

    suspend fun getData(): String
    suspend fun registerUser(email: String, password: String): Boolean
    suspend fun authenticateUser(email: String, password: String): String

}