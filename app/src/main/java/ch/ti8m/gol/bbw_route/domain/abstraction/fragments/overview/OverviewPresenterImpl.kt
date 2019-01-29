package ch.ti8m.gol.bbw_route.domain.abstraction.fragments.overview

import ch.ti8m.gol.bbw_route.domain.entity.SavedLocation
import ch.ti8m.gol.bbw_route.domain.entity.opendata.ConnectionsCall
import ch.ti8m.gol.bbw_route.domain.entity.openweathermap.WeatherForecast
import ch.ti8m.gol.bbw_route.domain.entity.search.CloseStation
import ch.ti8m.gol.bbw_route.persistence.repository.SavedLocationRepository
import ch.ti8m.gol.bbw_route.presentation.App
import ch.ti8m.gol.bbw_route.remote.connections.ConnectionsDataService
import ch.ti8m.gol.bbw_route.remote.connections.ConnectionsRetrofitInstance
import ch.ti8m.gol.bbw_route.remote.search.CloseStationsService
import ch.ti8m.gol.bbw_route.remote.search.SearchRetrofitInstance
import ch.ti8m.gol.bbw_route.remote.weather.WeatherDataService
import ch.ti8m.gol.bbw_route.remote.weather.WeatherRetrofitInstance
import org.apache.commons.text.WordUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class OverviewPresenterImpl(private val overviewView: OverviewView) : OverviewPresenter {

    private lateinit var savedLocationRepository: SavedLocationRepository
    private lateinit var savedLocation: SavedLocation

    override fun initRepository() {
        savedLocationRepository = App.savedLocationRepository
    }

    override fun handleCoordinates(lat: String, lon: String) {
        getWeatherForecast(lat, lon)
        getCurrentAddress(lat, lon)
    }

    private fun prepareWeatherInformation(weatherForecast: WeatherForecast) {
        val locationName = weatherForecast.locationName

        val celsiusTemp = weatherForecast.mainInformation.temp - 273.15
        val celsiusTempRounded = "%.2f".format(celsiusTemp)
        val celsiusString = "$celsiusTempRounded °C"

        val conditionTextBetterReadable = WordUtils.capitalizeFully(weatherForecast.weather[0].conditionDescription)
        val condition = "Condition: $conditionTextBetterReadable"

        val humidity = "Humidity: ${weatherForecast.mainInformation.humidity}%"

        val lat = "Latitude: ${weatherForecast.coordinates.lat}"

        val lon = "Longitude: ${weatherForecast.coordinates.lon}"

        val windDirection = "Wind-Direction: ${getWindDirection(weatherForecast.wind.direction)}"

        val windSpeed = "Wind-Speed: ${weatherForecast.wind.speed} m/s"

        val localDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.GERMANY)
        val now = Date()
        savedLocation = SavedLocation(
            "temp",
            celsiusTempRounded.toDouble(),
            localDateFormat.format(now),
            weatherForecast.coordinates.lat,
            weatherForecast.coordinates.lon
        )

        overviewView.onInitWeatherViews(
            locationName,
            celsiusString,
            condition,
            humidity,
            lat,
            lon,
            windDirection,
            windSpeed
        )
    }

    private fun getWeatherForecast(lat: String, lon: String) {
        val weatherDataService: WeatherDataService =
            WeatherRetrofitInstance.getRetrofitInstance().create(WeatherDataService::class.java)

        val call = weatherDataService.getWeatherForecast(lat, lon)

        call.enqueue(object : Callback<WeatherForecast> {
            override fun onResponse(call: Call<WeatherForecast>, response: Response<WeatherForecast>) {
                val weatherForecast = response.body()
                if (weatherForecast != null) {
                    prepareWeatherInformation(weatherForecast)
                } else {
                    overviewView.onWeatherLoadingFailure()
                }
            }

            override fun onFailure(call: Call<WeatherForecast>, t: Throwable) {
                overviewView.onWeatherLoadingFailure()
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
                savedLocation.name = currentAddress

                savedLocationRepository.saveSavedLocation(savedLocation)

                Timber.d("Last location saved.")

                getNextConnections(currentAddress)
            }

            override fun onFailure(call: Call<List<CloseStation>>, t: Throwable) {
                overviewView.onAddressLoadingFailure()
            }
        })

    }

    private fun getNextConnections(address: String) {
        val connectionsDataService: ConnectionsDataService =
            ConnectionsRetrofitInstance.getRetrofitInstance().create(ConnectionsDataService::class.java)
        val call = connectionsDataService.getNextConnections(address)

        call.enqueue(object : Callback<ConnectionsCall> {
            override fun onResponse(call: Call<ConnectionsCall>, response: Response<ConnectionsCall>) {
                val connections = response.body()!!.connections

                if (connections != null) {
                    overviewView.onLoadNextConnections(connections)
                } else {
                    overviewView.onConnectionsLoadingFailure()
                }
            }

            override fun onFailure(call: Call<ConnectionsCall>, t: Throwable) {
                overviewView.onConnectionsLoadingFailure()
            }
        })
    }

    private fun getWindDirection(direction: Double): String {
        var temp = "Unknown"
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
        return temp
    }

}