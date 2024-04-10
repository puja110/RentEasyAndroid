package com.example.renteasyandroid.feature.auth.data

import android.content.Context
import com.example.renteasyandroid.feature.auth.data.local.AuthLocalImpl
import com.example.renteasyandroid.feature.auth.data.remote.AuthRemoteImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Implementation of the AuthRepository interface for handling authentication operations.
 * This class combines local and remote authentication repositories to provide unified functionality.
 */
class AuthRepositoryImpl constructor(
    private val localRepository: AuthRepository.Local,
    private val remoteRepository: AuthRepository.Remote
) : AuthRepository {

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        /**
         * Singleton instance creation method.
         * It ensures only one instance of AuthRepositoryImpl is created and returned.
         */
        @Synchronized
        fun getInstance(context: Context): AuthRepository {
            if (instance != null) {
                return instance!!
            }

            // Initialize local and remote repositories
            val local = AuthLocalImpl.getInstance(context)
            val remote = AuthRemoteImpl.getInstance()

            // Create and return a new instance of AuthRepositoryImpl
            return AuthRepositoryImpl(local, remote).also { instance = it }
        }
    }

    /**
     * Example suspend function for fetching data.
     * This function performs its operation in the IO dispatcher using coroutines.
     */
    suspend fun getData(): String {
        return withContext(Dispatchers.IO) {
            // Perform data fetching operation here (placeholder)
            // In a real application, this method would fetch data from a data source
            ""
        }
    }

    /**
     * Suspend function to handle user signup.
     * It delegates the signup operation to the remote repository in the IO dispatcher.
     */
    override suspend fun signup(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            // Call the remote repository's register method for signup
            val response = remoteRepository.register(email, password)
            response
        }
    }

    /**
     * Suspend function to authenticate user credentials.
     * It delegates the authentication operation to the remote repository in the IO dispatcher.
     */
    override suspend fun authenticateUser(email: String, password: String): String {
        return withContext(Dispatchers.IO) {
            // Call the remote repository's authenticateUser method for authentication
            val response = remoteRepository.authenticateUser(email, password)
            response
        }
    }
}
