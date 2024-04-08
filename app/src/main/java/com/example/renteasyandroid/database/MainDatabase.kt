package com.example.renteasyandroid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.renteasyandroid.database.dao.RecentlyUpdatedDao
import com.example.renteasyandroid.database.dao.UserRatingDao
import com.example.renteasyandroid.database.dao.UsersDao
import com.example.renteasyandroid.database.entity.RecentlyUpdatedEntity
import com.example.renteasyandroid.database.entity.UserEntity
import com.example.renteasyandroid.database.entity.UserRatingEntity

@Database(
    entities = [
        UserEntity::class,
        RecentlyUpdatedEntity::class,
        UserRatingEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MainDatabase : RoomDatabase() {

    companion object {

        @Volatile
        var instance: MainDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MainDatabase {
            if (instance == null) {
                instance = createInstance(context)
            }
            return instance as MainDatabase
        }

        private fun createInstance(context: Context): MainDatabase {
            return Room
                .databaseBuilder(context, MainDatabase::class.java, "rent_easy_db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun getUsersDao(): UsersDao
    abstract fun getRecentlyUpdatedDao(): RecentlyUpdatedDao

    abstract fun getUserRatingDao(): UserRatingDao
}