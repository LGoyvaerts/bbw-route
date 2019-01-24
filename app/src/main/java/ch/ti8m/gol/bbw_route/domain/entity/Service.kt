package ch.ti8m.gol.bbw_route.domain.entity

import com.google.gson.annotations.SerializedName

data class Service(@SerializedName("regular") val regular: String, @SerializedName("irregular") val irregular: String)