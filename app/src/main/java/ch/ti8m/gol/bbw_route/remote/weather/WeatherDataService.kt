package ch.ti8m.gol.bbw_route.remote.weather

import ch.ti8m.gol.bbw_route.domain.entity.openweathermap.WeatherForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherDataService {

    @GET("data/2.5/weather?appid=9c408d6546877c6e9872f13b7ddcb1ae")
    fun getWeatherForecast(@Query("lat") lat: String, @Query("lon") lon: String): Call<WeatherForecast>
}