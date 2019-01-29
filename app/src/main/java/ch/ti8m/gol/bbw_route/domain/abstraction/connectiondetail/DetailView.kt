package ch.ti8m.gol.bbw_route.domain.abstraction.connectiondetail

import ch.ti8m.gol.bbw_route.domain.entity.opendata.Section

interface DetailView {

    fun onLoadSections(sections: List<Section>)
}