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

    //inserts the created users data to the local database
    override suspend fun register(email: String, password: String): Boolean {

        return try {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                email, password
            ).await()
            // Create a new user account with email and password
            true
        } catch (e: Exception) {
            // Handle registration failure
            val errorMessage = "Registration failed: ${e.message}"
            // You can log the error or handle it as needed
            false
        }
    }


    override suspend fun authenticateUser(email: String, password: String): String {
                    return try {
                        val response = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
                        response.user?.uid ?: ""
                    } catch (e: Exception) {
                        // Handle sign-in failure
                        "Sign-in failed: ${e.message}"
                    }

    }

}