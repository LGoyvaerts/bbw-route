package ch.ti8m.gol.bbw_route.domain.abstraction.fragments.overview

import ch.ti8m.gol.bbw_route.domain.entity.opendata.Connection
import ch.ti8m.gol.bbw_route.domain.entity.openweathermap.WeatherForecast

interface OverviewView {

    fun onInitRecyclerView()
    fun onInitWeatherViews(
        locationName: String,
        temperature: String,
        weatherCondition: String,
        humidity: String,
        lat: String,
        lon: String,
        windDirection: String,
        windSpeed: String
    )
    fun onLoadNextConnections(connections: List<Connection>)

    fun onWeatherLoadingFailure()
    fun onAddressLoadingFailure()
    fun onConnectionsLoadingFailure()
}