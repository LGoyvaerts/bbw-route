package ch.ti8m.gol.bbw_route.geo.impl

import android.app.Activity
import android.location.Location
import ch.ti8m.gol.bbw_route.geo.LocationService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationServiceImpl(private val activity: Activity):LocationService {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    init {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
    }


    override fun getLastKnownLocation(): Location {
        var temp = Location("TEMP")

    }
}