package ch.ti8m.gol.bbw_route.domain.entity.openweathermap

import com.google.gson.annotations.SerializedName

data class WeatherCoordinates(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double
)