package com.example.renteasyandroid.feature.auth.data.local

import android.content.Context
import com.example.renteasyandroid.database.DatabaseManager
import com.example.renteasyandroid.database.entity.UserEntity
import com.example.renteasyandroid.feature.auth.data.AuthRepository

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
    override suspend fun insert(userEntity: UserEntity): Boolean {
        val allData = databaseManager.getInstance().getUsersDao().getUsers()
        if (allData.isNotEmpty()) {
            allData.forEach {
                return if (it == userEntity) {
                    false
                } else {
                    databaseManager.getInstance().getUsersDao().insert(userEntity)
                    true
                }
            }
        } else {
            databaseManager.getInstance().getUsersDao().insert(userEntity)
            return true

        }
        return false
    }

    override suspend fun authenticateUser(email: String, password: String): String {
        val allData = databaseManager.getInstance().getUsersDao().getUsers()
        if (allData.isNotEmpty()) {
            allData.forEach {
                return if (it.email == email && it.password == password) {
                    "success"
                } else {
                    "Username and password did not match!"
                }
            }
        } else {
            return "Looks like you have not registered yet!"
        }
        return ""
    }
}