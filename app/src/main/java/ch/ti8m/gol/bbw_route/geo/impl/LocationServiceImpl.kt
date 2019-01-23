package ch.ti8m.gol.bbw_route.geo.impl

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.widget.Toast
import ch.ti8m.gol.bbw_route.geo.LocationService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationServiceImpl(private val activity: Activity) : LocationService {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34

    init {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
    }

    override fun getLastKnownLocation(): Location {
        var temp = Location("TEMP")
    }

}