package ch.ti8m.gol.bbw_route.domain.entity.opendata

import com.google.gson.annotations.SerializedName

data class Coordinate(@SerializedName("type") val type: String, @SerializedName("x") val x: Double, @SerializedName("y") val y: Double)