package ch.ti8m.gol.bbw_route.domain.entity.opendata

import com.google.gson.annotations.SerializedName

data class Journey(
    @SerializedName("name") val name: String,
    @SerializedName("category") val category: String,
    @SerializedName("categoryCode") val categoryCode: String?,
    @SerializedName("subcategory") val subcategory: String?,
    @SerializedName("number") val number: String,
    @SerializedName("operator") val operator: String,
    @SerializedName("to") val to: String,
    @SerializedName("passList") val passList: List<Stop>,
    @SerializedName("capacity1st") val capacityFirst: Long?,
    @SerializedName("capacity2nd") val capacitySecond: Long?
)