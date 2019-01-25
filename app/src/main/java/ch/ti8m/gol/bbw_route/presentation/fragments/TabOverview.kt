package ch.ti8m.gol.bbw_route.presentation.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ch.ti8m.gol.bbw_route.databinding.FragmentOverviewBinding
import ch.ti8m.gol.bbw_route.domain.entity.openweathermap.WeatherForecast
import ch.ti8m.gol.bbw_route.remote.weather.WeatherDataService
import ch.ti8m.gol.bbw_route.remote.weather.WeatherRetrofitInstance
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


class TabOverview : Fragment() {

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 99
    lateinit var binding: FragmentOverviewBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    companion object {

        fun newInstance(): TabOverview {
            val args: Bundle = Bundle()

            val fragment = TabOverview()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity as Activity)


    }

    override fun onStart() {
        super.onStart()

        if (!checkPermissions()) {
            requestPermissions()
        } else {
            getLastKnownLocation()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentOverviewBinding.inflate(inflater, container, false)

        //TODO init RecyclerView DataAdapter
        //TODO initViews()

        return binding.root
    }

    private fun getWeatherForecast(lat: String, lon: String) {
        //Create handle for RetrofitInstance interface
        val weatherDataService: WeatherDataService =
            WeatherRetrofitInstance.getRetrofitInstance().create(WeatherDataService::class.java)

        //TODO take lat/lon from locationService
        val call = weatherDataService.getWeatherForecast(lat, lon)

        call.enqueue(object : Callback<WeatherForecast> {
            override fun onResponse(call: Call<WeatherForecast>, response: Response<WeatherForecast>) {
                initWeatherViews(response.body()!!)
            }

            override fun onFailure(call: Call<WeatherForecast>, t: Throwable) {
                Toast.makeText(this@TabOverview.context, "WeatherCallback went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initWeatherViews(weatherForecast: WeatherForecast) {
        binding.overviewWeatherLocationTextview.text = weatherForecast.locationName

        val celsiusTemp = weatherForecast.mainInformation.temp - 273.15
        val celsiusTempRounded = "%.2f".format(celsiusTemp)
        val celsiusString = "$celsiusTempRounded °C"
        binding.overviewWeatherTemperatureTextview.text = celsiusString

        val condition = "Condition: ${weatherForecast.weather[0].conditionDescription}"
        binding.overviewWeatherConditionTextview.text = condition

        val humidity = "Humidity: ${weatherForecast.mainInformation.humidity}%"
        binding.overviewWeatherHumidityTextview.text = humidity

        val lat = "Latitude: ${weatherForecast.coordinates.lat}"
        binding.overviewWeatherLatTextview.text = lat

        val lon = "Longitude: ${weatherForecast.coordinates.lon}"
        binding.overviewWeatherLonTextview.text = lon
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                getWeatherForecast(it.result!!.latitude.toString(), it.result!!.longitude.toString())
            }
        }.addOnFailureListener {
            Timber.e(it)
        }
    }

    private fun checkPermissions() =
        ActivityCompat.checkSelfPermission(
            activity as Activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestPermissions() {
        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity as Activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            Toast.makeText(context, "Permission is needed", Toast.LENGTH_SHORT).show()

            // Request permission
            startLocationPermissionRequest()

        } else {
            // No explanation needed, we can request the permission.
            startLocationPermissionRequest()

            // REQUEST_PERMISSIONS_REQUEST_CODE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }

    }


    private fun startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(
            activity as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_PERMISSIONS_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSIONS_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // location-related task you need to do.

                    //TODO getLocation()
                    getLastKnownLocation()
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}
