package ch.ti8m.gol.bbw_route.domain.entity

data class Stop(
    val arrival: String,
    val departure: String,
    val name: String,
    val stopid: String,
    val x: Int,
    val y: Int
)