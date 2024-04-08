package com.example.renteasyandroid.utils

import android.content.Context
import android.content.SharedPreferences
object PreferenceHelper {

    val USERNAME = "_username"
    val PREF_NAME = "prefs"

    fun customPreference(context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.editPrefs(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.setUsername
        get() = getString(USERNAME, "")
        set(value) {
            editPrefs {
                it.putString(USERNAME, value)
            }
        }
}