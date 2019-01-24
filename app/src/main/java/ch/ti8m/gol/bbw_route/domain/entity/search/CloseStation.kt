package ch.ti8m.gol.bbw_route.domain.entity.search

import com.google.gson.annotations.SerializedName

data class CloseStation(
    @SerializedName("label") val label: String,
    @SerializedName("dist") val dist: Long,
    @SerializedName("iconclass") val iconclass: String
)