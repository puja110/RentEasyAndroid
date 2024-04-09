package com.example.renteasyandroid.feature.main.landing.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.renteasyandroid.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.Locale


class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 123
    }

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private var mMap: GoogleMap? = null
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Check if the app has location permission
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // Request location permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission already granted
            // Your code to handle location functionality here
            getCurrentLocation()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Check if permission is granted
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request location permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        // Enable the "My Location" layer if the permission has been granted.
        mMap?.isMyLocationEnabled = true

        mMap?.setOnMapClickListener { latlng -> // Clears the previously touched position
            mMap?.clear();
            // Animating to the touched position
            mMap?.animateCamera(CameraUpdateFactory.newLatLng(latlng));

            val location = LatLng(latlng.latitude, latlng.longitude)
            mMap?.addMarker(MarkerOptions().position(location))
        }
    }

    // Function to update marker position and animate camera
    private fun updateMarkerPositionAndAnimateCamera(latLng: LatLng) {
        // Clear previous markers
        mMap?.clear()

        // Animate camera to focus on the updated marker position
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    mMap?.isMyLocationEnabled = true
                    getCurrentLocation();

                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            } else {
                // Permission denied, show a message or handle the scenario gracefully.
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient?.lastLocation
            ?.addOnSuccessListener(
                this
            ) { location ->
                if (location != null) {
                    val latitude: Double = location.latitude
                    val longitude: Double = location.longitude
                    val address = getAddress(this, 44.39692173565016, -79.69086939012152)
                    updateMarkerPositionAndAnimateCamera(LatLng(latitude, longitude))
                    showAccommodationPositionAndAnimateCamera(LatLng(44.39692173565016, -79.69086939012152), address!!)
                } else {
                    // Location data is unavailable, handle accordingly
                }
            }
            ?.addOnFailureListener(this) {
                // Failed to get location, handle accordingly
            }
    }

    private fun showAccommodationPositionAndAnimateCamera(latLng: LatLng, address: String) {
        val markerOptions = MarkerOptions().position(latLng).title(address)
        mMap?.addMarker(markerOptions)
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
    }

    private fun getAddress(context: Context?, lat: Double, lng: Double): String? {
        val geocoder = context?.let { Geocoder(it, Locale.getDefault()) }
        return try {
            val addresses: List<Address>? = geocoder?.getFromLocation(lat, lng, 1)
            val obj: Address = addresses!![0]
            var add: String = obj.getAddressLine(0)
            add
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            null
        }
    }
}