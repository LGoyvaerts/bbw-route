package ch.ti8m.gol.bbw_route.domain.entity.opendata

import com.google.gson.annotations.SerializedName

data class Stop(
    @SerializedName("station") val station: Location,
    @SerializedName("arrival") val arrival: String?,
    @SerializedName("arrivalTimestamp") val arrivalTimestamp: Long?,
    @SerializedName("departure") val departure: String?,
    @SerializedName("departureTimestamp") val departureTimestamp: Long?,
    @SerializedName("delay") val delay: Long?,
    @SerializedName("platform") val platform: String?,
    @SerializedName("prognosis") val prognosis: Prognosis,
    @SerializedName("realtimeAvailability") val realtimeAvailability: String?,
    @SerializedName("location") val location: Location
)