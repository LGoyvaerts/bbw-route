package ch.ti8m.gol.bbw_route.domain.entity

import com.google.gson.annotations.SerializedName

data class Coordinate(@SerializedName("type") val type: String, @SerializedName("x") val x: Long, @SerializedName("y") val y: Long)