package ch.ti8m.gol.bbw_route.presentation

import android.app.Application
import ch.ti8m.gol.bbw_route.persistence.LocalSavedLocationRepository
import ch.ti8m.gol.bbw_route.persistence.persistence.SavedLocationDAO
import ch.ti8m.gol.bbw_route.persistence.persistence.SqlDatabase
import ch.ti8m.gol.bbw_route.persistence.repository.SavedLocationRepository

class App : Application() {

    private lateinit var sqlDatabase: SqlDatabase
    private lateinit var savedLocationDAO: SavedLocationDAO

    companion object {
        lateinit var savedLocationRepository: SavedLocationRepository
    }

    override fun onCreate() {
        super.onCreate()

        sqlDatabase = SqlDatabase(this)
        savedLocationDAO = SavedLocationDAO(sqlDatabase)
        savedLocationRepository = LocalSavedLocationRepository(savedLocationDAO)

    }
}