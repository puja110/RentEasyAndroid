package com.example.renteasyandroid.feature.main.data.local

import com.example.renteasyandroid.feature.main.data.MainRepository

class MainLocalImpl private constructor() : MainRepository.Local {

    companion object {
        @Volatile
        private var instance: MainRepository.Local? = null
        @Synchronized
        fun getInstance(): MainRepository.Local {
            if (instance != null) {
                return instance!!
            }
            return MainLocalImpl().also { instance = it }
        }
    }

}
