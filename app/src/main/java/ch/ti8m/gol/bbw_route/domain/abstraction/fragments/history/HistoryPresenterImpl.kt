package ch.ti8m.gol.bbw_route.domain.abstraction.fragments.history

import ch.ti8m.gol.bbw_route.persistence.repository.SavedLocationRepository
import ch.ti8m.gol.bbw_route.presentation.App

class HistoryPresenterImpl(private val historyView: HistoryView) :
    HistoryPresenter {

    private lateinit var savedLocationRepository: SavedLocationRepository

    override fun initRepository() {
        savedLocationRepository = App.savedLocationRepository
    }

    override fun loadSavedLocations() {
        val savedLocations = savedLocationRepository.getSavedLocations().reversed()
        if (!savedLocations.isEmpty()) {
            historyView.onLoadSavedLocations(savedLocations)
        } else {
            historyView.onNoLocationsFound()
        }
    }
}