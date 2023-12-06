package com.example.renteasyandroid.database

import android.content.Context

class DatabaseManager constructor(context: Context) {

    private val instance = MainDatabase.getInstance(context)

    fun getInstance(): MainDatabase = instance
}