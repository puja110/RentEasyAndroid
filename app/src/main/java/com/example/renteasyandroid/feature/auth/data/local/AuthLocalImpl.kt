package com.example.renteasyandroid.feature.auth.data.local

import android.content.Context
import com.example.renteasyandroid.database.DatabaseManager
import com.example.renteasyandroid.database.entity.UserEntity
import com.example.renteasyandroid.feature.auth.data.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthLocalImpl private constructor(
    private val databaseManager: DatabaseManager,
) : AuthRepository.Local {

    companion object {
        @Volatile
        private var instance: AuthRepository.Local? = null

        @Synchronized
        fun getInstance(context: Context): AuthRepository.Local {
            if (instance != null) {
                return instance!!
            }
            val database = DatabaseManager(context)
            return AuthLocalImpl(database).also { instance = it }
        }
    }

}