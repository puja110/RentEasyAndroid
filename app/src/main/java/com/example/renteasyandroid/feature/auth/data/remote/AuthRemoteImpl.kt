package com.example.renteasyandroid.feature.auth.data.remote

import com.example.renteasyandroid.feature.auth.data.AuthRepository
import com.example.renteasyandroid.remote.ApiService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

/**
 * Implementation of the AuthRepository.Remote interface for remote authentication operations.
 * This class handles registration and authentication of users using Firebase Authentication.
 */
class AuthRemoteImpl private constructor() : AuthRepository.Remote {

    // Lazy initialization of the ApiService
    private val apiService by lazy {
        ApiService.getInstance()
    }

    companion object {
        @Volatile
        private var instance: AuthRepository.Remote? = null

        /**
         * Singleton instance creation method for AuthRemoteImpl.
         * Ensures only one instance of AuthRemoteImpl is created and returned.
         */
        @Synchronized
        fun getInstance(): AuthRepository.Remote {
            if (instance != null) {
                return instance!!
            }
            return AuthRemoteImpl().also { instance = it }
        }
    }

    /**
     * Suspend function to register a new user with email and password.
     * This function interacts with Firebase Authentication to create a new user account.
     * Returns true if registration is successful, false otherwise.
     */
    override suspend fun register(email: String, password: String): Boolean {
        // Create a new user account with email and password
        return try {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()
            true // Registration successful
        } catch (e: Exception) {
            // Handle registration failure
            val errorMessage = "Registration failed: ${e.message}"
            // You can log the error or handle it as needed
            false // Registration failed
        }
    }

    /**
     * Suspend function to authenticate a user with email and password.
     * This function interacts with Firebase Authentication to sign in a user.
     * Returns a success message if authentication is successful, or an error message otherwise.
     */
    override suspend fun authenticateUser(email: String, password: String): String {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            // User credentials provided, proceed with authentication
            return try {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
                "success" // Authentication successful
            } catch (e: Exception) {
                // Handle sign-in failure
                "Sign-in failed: ${e.message}"
            }
        }
        // If email or password is empty, return an error message
        return "Username and password cannot be empty!"
    }
}
