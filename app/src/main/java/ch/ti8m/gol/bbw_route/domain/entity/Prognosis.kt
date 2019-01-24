package ch.ti8m.gol.bbw_route.domain.entity

import com.google.gson.annotations.SerializedName

data class Prognosis(
    @SerializedName("platform") val platform: String?,
    @SerializedName("arrival") val arrival: String?,
    @SerializedName("departure") val departure: String?,
    @SerializedName("capacity1st") val capacityFirst: Long?,
    @SerializedName("capacity2nd") val capacitySecond: Long?
)