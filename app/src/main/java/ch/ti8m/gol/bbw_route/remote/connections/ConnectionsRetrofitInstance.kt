package ch.ti8m.gol.bbw_route.remote.connections

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ConnectionsRetrofitInstance {
    companion object {
        private var retrofit: Retrofit? = null
        private val BASE_URL = "http://transport.opendata.ch/v1/"

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