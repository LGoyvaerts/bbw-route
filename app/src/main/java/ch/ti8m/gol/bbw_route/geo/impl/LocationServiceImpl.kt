package ch.ti8m.gol.bbw_route.geo.impl

import android.app.Activity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationServiceImpl(private val activity: Activity) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    init {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
    }
}