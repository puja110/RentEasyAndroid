package com.example.renteasyandroid.remote

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

interface ApiService {

    companion object {

        @Synchronized
        fun getInstance(): FirebaseFirestore {

            return Firebase.firestore
        }
    }
}