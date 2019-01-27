package ch.ti8m.gol.bbw_route.presentation.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ch.ti8m.gol.bbw_route.data.ConnectionsDataAdapter
import ch.ti8m.gol.bbw_route.databinding.FragmentOverviewBinding
import ch.ti8m.gol.bbw_route.domain.entity.opendata.ConnectionsCall
import ch.ti8m.gol.bbw_route.domain.entity.openweathermap.WeatherForecast
import ch.ti8m.gol.bbw_route.domain.entity.search.CloseStation
import ch.ti8m.gol.bbw_route.remote.connections.ConnectionsDataService
import ch.ti8m.gol.bbw_route.remote.connections.ConnectionsRetrofitInstance
import ch.ti8m.gol.bbw_route.remote.search.CloseStationsService
import ch.ti8m.gol.bbw_route.remote.search.SearchRetrofitInstance
import ch.ti8m.gol.bbw_route.remote.weather.WeatherDataService
import ch.ti8m.gol.bbw_route.remote.weather.WeatherRetrofitInstance
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.apache.commons.text.WordUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*


class TabOverview : Fragment() {

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 99
    lateinit var binding: FragmentOverviewBinding
    lateinit var connectionsDataAdapter: ConnectionsDataAdapter
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

        connectionsDataAdapter = ConnectionsDataAdapter(Collections.emptyList())

        return binding.root
    }

    private fun handleCoordinates(lat: String, lon: String) {
        initRecyclerView()
        getWeatherForecast(lat, lon)
        getCurrentAddress(lat, lon)

    }

    private fun initRecyclerView() {
        val recyclerView = binding.overviewConnectionsRecyclerview
        recyclerView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = connectionsDataAdapter
    }

    private fun getWeatherForecast(lat: String, lon: String) {
        //Create handle for RetrofitInstance interface
        val weatherDataService: WeatherDataService =
            WeatherRetrofitInstance.getRetrofitInstance().create(WeatherDataService::class.java)

        val call = weatherDataService.getWeatherForecast(lat, lon)

        call.enqueue(object : Callback<WeatherForecast> {
            override fun onResponse(call: Call<WeatherForecast>, response: Response<WeatherForecast>) {
                initWeatherViews(response.body()!!)
            }

            override fun onFailure(call: Call<WeatherForecast>, t: Throwable) {
                Toast.makeText(this@TabOverview.context, "WeatherCallback went wrong", Toast.LENGTH_SHORT).show()
                Timber.e("Weather Callback went wrong: $t")
            }
        })
    }

    private fun getCurrentAddress(lat: String, lon: String) {
        //Create handle for RetrofitInstance interface
        val closeStationsService: CloseStationsService =
            SearchRetrofitInstance.getRetrofitInstance().create(CloseStationsService::class.java)
        val call = closeStationsService.getCloseStations("$lat,$lon")

        call.enqueue(object : Callback<List<CloseStation>> {
            override fun onResponse(call: Call<List<CloseStation>>, response: Response<List<CloseStation>>) {
                val currentAddress = response.body()!![0].label
                getNextConnections(currentAddress)
            }

            override fun onFailure(call: Call<List<CloseStation>>, t: Throwable) {
                Toast.makeText(this@TabOverview.context, "CloseStation Callback went wrong", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun getNextConnections(address: String) {
        val connectionsDataService: ConnectionsDataService =
            ConnectionsRetrofitInstance.getRetrofitInstance().create(ConnectionsDataService::class.java)
        val call = connectionsDataService.getNextConnections(address)

        call.enqueue(object : Callback<ConnectionsCall> {
            override fun onResponse(call: Call<ConnectionsCall>, response: Response<ConnectionsCall>) {
                val connectionsCall = response.body()!!
                connectionsDataAdapter.setConnections(connectionsCall.connections!!)
            }

            override fun onFailure(call: Call<ConnectionsCall>, t: Throwable) {
                Toast.makeText(this@TabOverview.context, "ConnectionsCall Callback went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun initWeatherViews(weatherForecast: WeatherForecast) {
        binding.overviewWeatherLocationTextview.text = weatherForecast.locationName

        val celsiusTemp = weatherForecast.mainInformation.temp - 273.15
        val celsiusTempRounded = "%.2f".format(celsiusTemp)
        val celsiusString = "$celsiusTempRounded °C"
        binding.overviewWeatherTemperatureTextview.text = celsiusString

        val conditionTextBetterReadable = WordUtils.capitalizeFully(weatherForecast.weather[0].conditionDescription)
        val condition = "Condition: $conditionTextBetterReadable"
        binding.overviewWeatherConditionTextview.text = condition

        val humidity = "Humidity: ${weatherForecast.mainInformation.humidity}%"
        binding.overviewWeatherHumidityTextview.text = humidity

        val lat = "Latitude: ${weatherForecast.coordinates.lat}"
        binding.overviewWeatherLatTextview.text = lat

        val lon = "Longitude: ${weatherForecast.coordinates.lon}"
        binding.overviewWeatherLonTextview.text = lon
    }

    private fun getWindDirection(direction: Double): String {
        var temp = ""
        if (direction >= 337.5 || direction < 22.5) {
            temp = "N"
        } else if (direction >= 22.5 && direction < 67.5) {
            temp = "NE"
        } else if (direction >= 67.5 && direction < 112.5) {
            temp = "E"
        } else if (direction >= 112.5 && direction < 157.5) {
            temp = "SE"
        } else if (direction >= 157.5 && direction < 202.5) {
            temp = "S"
        } else if (direction >= 202.5 && direction < 247.5) {
            temp = "SW"
        } else if (direction >= 247.5 && direction < 292.5) {
            temp = "W"
        } else if (direction >= 292.5 && direction < 300) {
            temp = "NW"
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                handleCoordinates(it.result!!.latitude.toString(), it.result!!.longitude.toString())
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
