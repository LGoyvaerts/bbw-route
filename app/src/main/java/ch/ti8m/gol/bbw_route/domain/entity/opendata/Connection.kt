package ch.ti8m.gol.bbw_route.domain.entity.opendata

import com.google.gson.annotations.SerializedName

data class Connection(
    @SerializedName("from") val from: Stop?,
    @SerializedName("to") val to: Stop?,
    @SerializedName("duration") val duration: String,
    @SerializedName("transfers") val transfers: Long,
    @SerializedName("service") val service: Service?,
    @SerializedName("products") val products: List<String>,
    @SerializedName("capacity1st") val capacityFirst: Long?,
    @SerializedName("capacity2nd") val capacitySecond: Long?,
    @SerializedName("sections") val sections: List<Section>
)