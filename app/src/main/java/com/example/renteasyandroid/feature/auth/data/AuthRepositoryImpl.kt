package com.example.renteasyandroid.feature.auth.data

import android.content.Context
import com.example.renteasyandroid.database.entity.UserEntity
import com.example.renteasyandroid.feature.auth.data.local.AuthLocalImpl
import com.example.renteasyandroid.feature.auth.data.remote.AuthRemoteImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepositoryImpl constructor(
    private val localRepository: AuthRepository.Local,
    private val remoteRepository: AuthRepository.Remote
) : AuthRepository {

    companion object {
        @Volatile
        private var instance: AuthRepository? = null

        @Synchronized
        fun getInstance(context: Context): AuthRepository {
            if (instance != null) {
                return instance!!
            }

            val local = AuthLocalImpl.getInstance(context)
            val remote = AuthRemoteImpl.getInstance()
            return AuthRepositoryImpl(local, remote).also { instance = it }
        }
    }

    suspend fun getData(): String {
        return withContext(Dispatchers.IO) {
            ""
        }
    }

     override suspend fun signup(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val response = remoteRepository.register(email, password)
            response
        }
    }

    override suspend fun authenticateUser(email: String, password: String): String {
        return withContext(Dispatchers.IO) {
            val response = remoteRepository.authenticateUser(email, password)
            response
        }
    }

}