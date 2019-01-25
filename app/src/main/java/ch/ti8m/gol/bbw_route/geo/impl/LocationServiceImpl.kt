package ch.ti8m.gol.bbw_route.geo.impl

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import ch.ti8m.gol.bbw_route.geo.LocationService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationServiceImpl(activity: Activity) : LocationService {

    private var fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(activity)
    private lateinit var myLocation: Location

    @SuppressLint("MissingPermission")
    override fun getLastKnownLocation(): Location {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            myLocation = it
        }.addOnFailureListener {

        }
        return myLocation
    }

}