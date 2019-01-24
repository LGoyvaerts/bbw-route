package ch.ti8m.gol.bbw_route.domain.entity.opendata

import com.google.gson.annotations.SerializedName

data class Section(
    @SerializedName("journey") val journey: Journey?,
    @SerializedName("walk") val walk: Walk?,
    @SerializedName("departure") val departure: Stop,
    @SerializedName("arrival") val arrival: Stop
)