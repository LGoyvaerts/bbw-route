package ch.ti8m.gol.bbw_route.domain.abstraction.connectiondetail

import ch.ti8m.gol.bbw_route.domain.entity.opendata.Connection
import com.google.gson.Gson

class DetailPresenterImpl(private val detailView: DetailView) : DetailPresenter {

    override fun parseCurrentConnection(currentConnectionString: String) {
        val gson = Gson()
        val currentConnection = gson.fromJson(currentConnectionString, Connection::class.java)
        detailView.onLoadSections(currentConnection.sections)
    }
}