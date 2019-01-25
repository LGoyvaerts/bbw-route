package ch.ti8m.gol.bbw_route.remote

import ch.ti8m.gol.bbw_route.domain.entity.openweathermap.WeatherForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface WeatherDataService {

    @GET("data/2.5/weather?lat={lat}&lon={lon}&appid=9c408d6546877c6e9872f13b7ddcb1ae")
    fun getWeatherForecast(@Path("lat") lat: String, @Path("long") lon: String): Call<WeatherForecast>
}