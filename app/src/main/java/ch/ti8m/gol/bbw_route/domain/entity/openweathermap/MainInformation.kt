package ch.ti8m.gol.bbw_route.domain.entity.openweathermap

import com.google.gson.annotations.SerializedName

data class MainInformation(
    @SerializedName("temp") val temp: Double,
    @SerializedName("pressure") val pressure: Long,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("temp_min") val minTemp: Double,
    @SerializedName("temp_max") val maxTemp: Double
)