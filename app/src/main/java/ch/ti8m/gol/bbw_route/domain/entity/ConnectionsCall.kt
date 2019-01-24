package ch.ti8m.gol.bbw_route.domain.entity

class ConnectionsCall(val connections:List<Connection>?, val from:Location, val to:Location, val stations:ConnectionSummary) {
}