package ch.ti8m.gol.bbw_route.remote.search

import ch.ti8m.gol.bbw_route.domain.entity.search.CloseStation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CloseStationsService {

    @GET("completion.json?accuracy=10")
    fun getCloseStations(@Query("latlon") latlon: String): Call<List<CloseStation>> //latlon=lat,lon
}