package ch.ti8m.gol.bbw_route.domain.entity

import com.google.gson.annotations.SerializedName

data class Stop(
    @SerializedName("arrival") val arrival: String,
    @SerializedName("departure") val departure: String,
    @SerializedName("name") val name: String,
    @SerializedName("stopid") val stopid: String,
    @SerializedName("x") val x: Int,
    @SerializedName("y") val y: Int
)