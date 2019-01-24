package ch.ti8m.gol.bbw_route.remote

import retrofit2.http.GET
import retrofit2.http.Path


interface APIInterface {

    companion object {
        val opendata_baseurl = "http://transport.opendata.ch/v1/"
        val search_baseurl = "https://fahrplan.search.ch/api/"
    }

    @GET("connections?from={location}&to=Winterthur")
    fun getConnections(@Path("location") location: String)  //: Call<ConnectionsCall>

    @GET("completion.json?latlon={lat},{lon}&accuracy=10")
    fun getCloseStations(@Path("lat") lat: Float, @Path("lon") lon: Float)  //: Call<List<CloseStation>>

}