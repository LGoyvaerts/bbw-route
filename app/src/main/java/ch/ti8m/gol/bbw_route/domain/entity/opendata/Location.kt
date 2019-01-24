package ch.ti8m.gol.bbw_route.domain.entity.opendata

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("score") val score: Int?,
    @SerializedName("coordinate") val coordinate: Coordinate,
    @SerializedName("distance") val distance: Long?
)