package ch.ti8m.gol.bbw_route.domain.entity.openweathermap

import com.google.gson.annotations.SerializedName

data class WeatherForecast(
    @SerializedName("coord") val coordinates: WeatherCoordinates,
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("base") val base: String,
    @SerializedName("main") val mainInformation: MainInformation,
    @SerializedName("visibility") val visibility: Long,
    @SerializedName("wind") val wind: Wind,
    @SerializedName("clouds") val clouds: Cloud,
    @SerializedName("dt") val dt: Long,
    @SerializedName("sys") val internalInformation: InternalInformation,
    @SerializedName("id") val id: Long,
    @SerializedName("name") val locationName: String,
    @SerializedName("cod") val cod: Int
)