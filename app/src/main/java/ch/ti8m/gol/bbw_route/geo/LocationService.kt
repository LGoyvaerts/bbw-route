package ch.ti8m.gol.bbw_route.geo

import android.location.Location

interface LocationService {

    fun getLastKnownLocation(): Location
}