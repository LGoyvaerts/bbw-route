package ch.ti8m.gol.bbw_route.presentation.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ch.ti8m.gol.bbw_route.data.ConnectionsDataAdapter
import ch.ti8m.gol.bbw_route.databinding.FragmentOverviewBinding
import ch.ti8m.gol.bbw_route.domain.abstraction.fragments.overview.OverviewPresenter
import ch.ti8m.gol.bbw_route.domain.abstraction.fragments.overview.OverviewPresenterImpl
import ch.ti8m.gol.bbw_route.domain.abstraction.fragments.overview.OverviewView
import ch.ti8m.gol.bbw_route.domain.entity.opendata.Connection
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import timber.log.Timber
import java.util.*


class TabOverview : Fragment(), OverviewView, SwipeRefreshLayout.OnRefreshListener {

    private val REQUEST_PERMISSIONS_REQUEST_CODE = 99
    lateinit var binding: FragmentOverviewBinding
    lateinit var connectionsDataAdapter: ConnectionsDataAdapter
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var overviewPresenter: OverviewPresenter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

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

        overviewPresenter = OverviewPresenterImpl(this)
        overviewPresenter.initRepository()
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
        initRecyclerView()

        return binding.root
    }

    private fun initRecyclerView() {
        val recyclerView = binding.overviewConnectionsRecyclerview
        recyclerView.setHasFixedSize(true)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = connectionsDataAdapter

        //Set SwipeRefreshLayout
        swipeRefreshLayout = binding.overviewNextConnectionsRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.post {
            run {
                dispatchRefresh()
            }
        }
    }

    override fun onInitWeatherViews(
        locationName: String,
        temperature: String,
        weatherCondition: String,
        humidity: String,
        lat: String,
        lon: String,
        windDirection: String,
        windSpeed: String
    ) {
        binding.overviewWeatherLocationTextview.text = locationName
        binding.overviewWeatherTemperatureTextview.text = temperature
        binding.overviewWeatherConditionTextview.text = weatherCondition
        binding.overviewWeatherHumidityTextview.text = humidity
        binding.overviewWeatherLatTextview.text = lat
        binding.overviewWeatherLonTextview.text = lon
        binding.overviewWeatherWindDirectionTextview.text = windDirection
        binding.overviewWeatherWindSpeedTextview.text = windSpeed
    }

    override fun onLoadNextConnections(connections: List<Connection>) {
        connectionsDataAdapter.setConnections(connections)
    }


    override fun onWeatherLoadingFailure() {
        Toast.makeText(context, "WeatherCallback went wrong", Toast.LENGTH_SHORT).show()
    }

    override fun onAddressLoadingFailure() {
        Toast.makeText(context, "AddressCallback went wrong", Toast.LENGTH_SHORT).show()
    }

    override fun onConnectionsLoadingFailure() {
        Toast.makeText(context, "ConnectionsCallback went wrong", Toast.LENGTH_SHORT).show()
    }

    private fun dispatchRefresh() {
        binding.overviewNextConnectionsRefreshLayout.isRefreshing = true

        getLastKnownLocation()

        binding.overviewNextConnectionsRefreshLayout.isRefreshing = false
    }

    override fun onRefresh() {
        dispatchRefresh()
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation() {
        fusedLocationProviderClient.lastLocation.addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                overviewPresenter.handleCoordinates(it.result!!.latitude.toString(), it.result!!.longitude.toString())
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
