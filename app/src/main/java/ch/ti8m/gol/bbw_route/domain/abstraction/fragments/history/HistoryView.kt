package ch.ti8m.gol.bbw_route.domain.abstraction.fragments.history

import ch.ti8m.gol.bbw_route.domain.entity.SavedLocation

interface HistoryView {

    fun onLoadSavedLocations(savedLocations: List<SavedLocation>)
    fun onNoLocationsFound()
}