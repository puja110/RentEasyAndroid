package com.example.renteasyandroid.feature.auth.data

import com.example.renteasyandroid.database.entity.UserEntity

interface AuthRepository {
    interface Local {
        suspend fun insert(userEntity: UserEntity): Boolean
        suspend fun authenticateUser(email: String, password: String): String
    }

    interface Remote {

    }

    suspend fun getData(): String
    suspend fun insert(userEntity: UserEntity): Boolean
    suspend fun authenticateUser(email: String, password: String): String

}