package com.example.renteasyandroid.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.renteasyandroid.R
import com.example.renteasyandroid.utils.Permissons.checkFineLocation
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

fun checkNetworkAvailability(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val capabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (capabilities != null) {
        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_WIFI
            )
        ) {
            return true
        }
    }
    return false
}

fun Activity.showToast(title: String, message: String, type: MotionToastStyle) {
    if (isValidContext(this)) {
        MotionToast.darkToast(
            this,
            title,
            message,
            type,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this, R.font.sansf_regular)
        )
    }
}

fun Fragment.showToast(title: String, message: String, type: MotionToastStyle) {
    if (isValidContext(context)) {
        MotionToast.darkToast(
            requireActivity(),
            title,
            message,
            type,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(requireContext(), R.font.sansf_regular)
        )
    }
}

fun isValidContext(context: Context?): Boolean {
    if (context == null) {
        return false
    }
    if (context is Activity) {
        val activity: Activity = context
        return !activity.isDestroyed && !activity.isFinishing
    }
    return true
}

inline fun <R> R?.orElse(block: () -> R): R {
    return this ?: block()
}

@SuppressLint("MissingPermission")
fun Activity.gpsProvider(): Location? {
    if (this.checkFineLocation()) {
        val locationManager = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    }
    return null
}