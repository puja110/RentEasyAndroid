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
    override suspend fun registerUser(userEntity: UserEntity): Boolean {
        val allData = databaseManager.getInstance().getUsersDao().getUsers()
        if (allData.isNotEmpty()) {
            for (existingUser in allData) {
                if (existingUser == userEntity) {
                    // User already exists, return false
                    return false
                }
            }
        }

        // Create a new user account with email and password
        return try {
            userEntity.email?.let {
                userEntity.password?.let { it1 ->
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        it, it1
                    ).await()
                }
            }
            // User registered successfully
            // You can perform additional actions here (e.g., store additional user data)
            databaseManager.getInstance().getUsersDao().insert(userEntity)
            true
        } catch (e: Exception) {
            // Handle registration failure
            val errorMessage = "Registration failed: ${e.message}"
            // You can log the error or handle it as needed
            false
        }
    }


    override suspend fun authenticateUser(email: String, password: String): String {
        val allData = databaseManager.getInstance().getUsersDao().getUsers()
        if (allData.isNotEmpty()) {
            for (userEntity in allData) {
                if (userEntity.email == email && userEntity.password == password) {
                    // User found in local data, now authenticate with Firebase
                    return try {
                        val response = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
                        response.user?.uid ?: ""
                    } catch (e: Exception) {
                        // Handle sign-in failure
                        "Sign-in failed: ${e.message}"
                    }
                }
            }
            // If the loop completes without finding a matching user
            return "Username and password did not match!"
        } else {
            return "Looks like you have not registered yet!"
        }
    }

}