package ch.ti8m.gol.bbw_route.remote

import retrofit2.Call
import retrofit2.http.GET
import java.util.*


interface WeatherDataService {

    //TODO
    @GET("bins/1bsqcn/")
    fun getNoticeData(): Call<Objects>
}