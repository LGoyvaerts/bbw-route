package ch.ti8m.gol.bbw_route.domain.entity.opendata

import com.google.gson.annotations.SerializedName

data class ConnectionSummary(
    @SerializedName("from") val from: List<Location>,
    @SerializedName("to") val to: List<Location>
)