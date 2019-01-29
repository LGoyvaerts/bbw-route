package ch.ti8m.gol.bbw_route.domain.entity.openweathermap

import com.google.gson.annotations.SerializedName

data class Cloud(@SerializedName("all") val percentage: Int)