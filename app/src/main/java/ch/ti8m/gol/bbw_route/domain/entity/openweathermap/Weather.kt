package ch.ti8m.gol.bbw_route.domain.entity.openweathermap

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("id") val id: Long,
    @SerializedName("main") val mainCondition: String,
    @SerializedName("description") val conditionDescription: String,
    @SerializedName("icon") val icon: String
)