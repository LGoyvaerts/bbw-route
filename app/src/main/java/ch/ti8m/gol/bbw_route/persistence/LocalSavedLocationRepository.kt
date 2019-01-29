package ch.ti8m.gol.bbw_route.persistence

import ch.ti8m.gol.bbw_route.domain.entity.SavedLocation
import ch.ti8m.gol.bbw_route.persistence.persistence.SavedLocationDAO
import ch.ti8m.gol.bbw_route.persistence.repository.SavedLocationRepository

class LocalSavedLocationRepository(private val savedLocationDAO: SavedLocationDAO) : SavedLocationRepository {
    override fun saveSavedLocation(savedLocation: SavedLocation) {
        savedLocationDAO.insertSavedLocation(savedLocation)
    }

    override fun getSavedLocations(): List<SavedLocation> {
        return savedLocationDAO.selectSavedLocations()
    }

    override fun clear() {
        savedLocationDAO.clear()
    }

}