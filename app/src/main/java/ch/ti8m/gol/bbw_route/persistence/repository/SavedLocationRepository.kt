package ch.ti8m.gol.bbw_route.persistence.repository

import ch.ti8m.gol.bbw_route.domain.entity.SavedLocation

interface SavedLocationRepository {

    fun saveSavedLocation(savedLocation: SavedLocation)

    fun getSavedLocations():List<SavedLocation>

    fun clear()
}