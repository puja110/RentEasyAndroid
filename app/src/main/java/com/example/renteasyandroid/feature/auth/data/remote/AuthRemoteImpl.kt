package com.example.renteasyandroid.feature.auth.data.remote

import android.content.Context
import com.example.renteasyandroid.feature.auth.data.AuthRepository
import com.example.renteasyandroid.remote.ApiService
import com.example.renteasyandroid.utils.SharedPreferenceManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

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

    //inserts the created users data to the local database
    override suspend fun register(email: String, password: String): Boolean {

        // Create a new user account with email and password
        return try {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                email, password
            ).await()
            true
        } catch (e: Exception) {
            // Handle registration failure
            val errorMessage = "Registration failed: ${e.message}"
            // You can log the error or handle it as needed
            false
        }
    }


    override suspend fun authenticateUser(email: String, password: String): String {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            // User found in local data, now authenticate with Firebase
            return try {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
                "success"
            } catch (e: Exception) {
                // Handle sign-in failure
                "Sign-in failed: ${e.message}"
            }
        }
        // If the loop completes without finding a matching user
        return "Username and password did not match!"
    }
}