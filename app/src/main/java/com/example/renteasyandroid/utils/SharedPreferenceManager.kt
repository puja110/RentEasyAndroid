package com.example.renteasyandroid.utils

import android.content.Context
import androidx.core.content.edit

class SharedPreferenceManager constructor(var context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(Constants.PREF_FILE, Context.MODE_PRIVATE)

    var email: String?
        get() = sharedPreferences.getString(Constants.PREF_EMAIL, "")
        set(value) = sharedPreferences.edit { putString(Constants.PREF_EMAIL, value) }

    var password: String?
        get() = sharedPreferences.getString(Constants.PREF_PASSWORD, "")
        set(value) = sharedPreferences.edit { putString(Constants.PREF_PASSWORD, value) }

    var accessToken: String?
        get() = sharedPreferences.getString(Constants.PREF_TOKEN, "")
        set(value) = sharedPreferences.edit() { putString(Constants.PREF_TOKEN, value) }

    var uuid: String?
        get() = sharedPreferences.getString(Constants.PREF_UUID, "")
        set(value) = sharedPreferences.edit() { putString(Constants.PREF_UUID, value) }

}