package com.example.renteasyandroid.remote

import android.annotation.SuppressLint
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

interface FirebaseApiService {

    companion object {

        @SuppressLint("StaticFieldLeak")
        private var apiService: FirebaseFirestore? = null

        @Synchronized
        fun getInstance(): FirebaseFirestore {
            if (apiService != null) {
                return apiService!!
            }
            apiService = Firebase.firestore
            return apiService!!
        }
    }
}