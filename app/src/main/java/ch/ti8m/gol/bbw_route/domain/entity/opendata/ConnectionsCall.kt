package ch.ti8m.gol.bbw_route.domain.entity.opendata

import com.google.gson.annotations.SerializedName

class ConnectionsCall(
    @SerializedName("connections") val connections: List<Connection>?,
    @SerializedName("from") val from: Location,
    @SerializedName("to") val to: Location,
    @SerializedName("stations") val stations: ConnectionSummary
) {
}