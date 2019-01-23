package ch.ti8m.gol.bbw_route.domain.entity

data class Exit(
    val arrival: String,
    val stopid: String,
    val x: Int,
    val y: Int,
    val name: String,
    val sbb_name: String,
    val waittime: Int?,
    val track: String?
)