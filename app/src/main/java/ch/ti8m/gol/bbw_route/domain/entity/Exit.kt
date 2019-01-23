package ch.ti8m.gol.bbw_route.domain.entity

import com.google.gson.annotations.SerializedName

data class Exit(
    @SerializedName("arrival") val arrival: String,
    @SerializedName("stopid") val stopid: String,
    @SerializedName("x") val x: Int,
    @SerializedName("y") val y: Int,
    @SerializedName("name") val name: String,
    @SerializedName("sbb_name") val sbb_name: String,
    @SerializedName("waittime") var waittime: Int? = 0,
    @SerializedName("track") var track: String? = null
)