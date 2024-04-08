package com.example.renteasyandroid.utils

import android.content.Context
import android.content.SharedPreferences
object PreferenceHelper {

    val REMINDER_TIME = "_time"
    val REMINDER_TYPE = "_type"
    val REMINDER_EMAIL = "_email"
    val REMINDER_PASSWORD = "_password"
    val REMINDER_NAME = "_name"
    val REMEMBER_LOGIN = "_remember"
    val REMEMBER_IMAGE = "_image"
    val CUSTOM_PREF_NAME = "prefs"

    fun customPreference(context: Context): SharedPreferences =
        context.getSharedPreferences(CUSTOM_PREF_NAME, Context.MODE_PRIVATE)

    inline fun SharedPreferences.editPrefs(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.setTime
        get() = getString(REMINDER_TIME, "")
        set(value) {
            editPrefs {
                it.putString(REMINDER_TIME, value)
            }
        }

    var SharedPreferences.setImage
        get() = getString(REMEMBER_IMAGE, "")
        set(value) {
            editPrefs {
                it.putString(REMEMBER_IMAGE, value)
            }
        }

    var SharedPreferences.setName
        get() = getString(REMINDER_NAME, "")
        set(value) {
            editPrefs {
                it.putString(REMINDER_NAME, value)
            }
        }

    var SharedPreferences.setType
        get() = getString(REMINDER_TYPE, "")
        set(value) {
            editPrefs {
                it.putString(REMINDER_TYPE, value)
            }
        }

    var SharedPreferences.setEmail
        get() = getString(REMINDER_EMAIL, "")
        set(value) {
            editPrefs {
                it.putString(REMINDER_EMAIL, value)
            }
        }

    var SharedPreferences.setPassword
        get() = getString(REMINDER_PASSWORD, "")
        set(value) {
            editPrefs {
                it.putString(REMINDER_PASSWORD, value)
            }
        }

    var SharedPreferences.setRememberLogin
        get() = getBoolean(REMEMBER_LOGIN, false)
        set(value) {
            editPrefs {
                it.putBoolean(REMEMBER_LOGIN, value)
            }
        }

    var SharedPreferences.clearValues
        get() = { }
        set(value) {
            editPrefs {
                it.clear().commit()
            }
        }
}