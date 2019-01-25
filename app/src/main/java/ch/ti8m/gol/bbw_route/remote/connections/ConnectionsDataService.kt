package ch.ti8m.gol.bbw_route.remote.connections

import ch.ti8m.gol.bbw_route.domain.entity.opendata.ConnectionsCall
import ch.ti8m.gol.bbw_route.domain.entity.openweathermap.WeatherForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ConnectionsDataService {

    @GET("connections?to=Winterthur&limit=5")
    fun getNextConnections(@Query("from") address: String): Call<ConnectionsCall>
}