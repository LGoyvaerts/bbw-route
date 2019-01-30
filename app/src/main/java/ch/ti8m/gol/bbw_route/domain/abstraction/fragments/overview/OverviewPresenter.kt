package ch.ti8m.gol.bbw_route.domain.abstraction.fragments.overview

import ch.ti8m.gol.bbw_route.domain.entity.openweathermap.WeatherForecast

interface OverviewPresenter {

    fun initRepository()
    fun handleCoordinates(lat: String, lon: String)
}