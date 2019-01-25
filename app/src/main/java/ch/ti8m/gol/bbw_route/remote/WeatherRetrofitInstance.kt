package ch.ti8m.gol.bbw_route.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class WeatherRetrofitInstance {
    companion object {
        private var retrofit: Retrofit? = null
        private val BASE_URL = "http://api.openweathermap.org/"

        /**
         * Create an instance of Retrofit object
         */
        fun getRetrofitInstance(): Retrofit {
            if (retrofit == null) {
                retrofit = retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
    }
}